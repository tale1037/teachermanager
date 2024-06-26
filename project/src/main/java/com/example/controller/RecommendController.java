package com.example.controller;

import com.example.pojo.Appointment;
import com.example.pojo.FreeTime;
import com.example.pojo.Rating;
import com.example.pojo.Result;
import com.example.service.Impl.AppointmentService;
import com.example.service.Impl.FreeTimeService;
import com.example.service.Impl.RatingService;
import com.example.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class RecommendController {
    @Autowired
    private FreeTimeService freeTimeService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private RatingService ratingService;
    //推荐
    @GetMapping("/recommend/freetime")
    public Result recommend(@RequestHeader(name = "Authorization", required = false) String token,
                            HttpServletResponse response,
                            @RequestParam String id) {
        try {
            int ID = Integer.parseInt(id);
            Appointment appointment = appointmentService.findByID(ID);
            //Map<String, Object> claims = JwtUtil.parseToken(token);
            //String email = (String) claims.get("email");
            List<Rating> ratings = ratingService.findAll();
            System.out.println(ratings);
            if(ratings.size()<1){
                List<Appointment> lastappointments=appointmentService.findByStudentEmail(appointment.getStudentEmail());
                System.out.println(lastappointments);
                if(lastappointments==null){
                    System.out.println(freeTimeService.findAllNo());
                   return Result.success(freeTimeService.findAllNo());
                }
                else {
                    List<FreeTime> topFreeTimeLast =new ArrayList<>();
                    for (Appointment lastappointment : lastappointments){
                        if(appointment.getTeacherEmail().equals(lastappointment.getTeacherEmail())){
                            continue;
                        }
                        else {
                            List<FreeTime> otherTeacherFreetime=freeTimeService.findByTeacherEmail(lastappointment.getTeacherEmail());
                            for (FreeTime freeTime : otherTeacherFreetime){
                                if(!freeTime.isTstatus()){
                                    topFreeTimeLast.add(freeTime);
                                }
                            }
                        }
                    }
                    System.out.println(topFreeTimeLast);
                    if(topFreeTimeLast.isEmpty()){
                        return Result.success(freeTimeService.findAllNo());
                    }
                    else {
                        return Result.success(topFreeTimeLast);
                    }
                }
            }
            else {
                // 执行UV分解并生成推荐结果
                Set<String> userSet = new HashSet<>();
                Set<String> itemSet = new HashSet<>();
                for (Rating rating : ratings) {
                    userSet.add(rating.getStudentEmail());
                    itemSet.add(rating.getTeacherEmail());
                }
                List<String> users = new ArrayList<>(userSet);
                List<String> items = new ArrayList<>(itemSet);
                RealMatrix[] matrices = runUVDecomposition(ratings,users,items);
                RealMatrix U = matrices[0];
                RealMatrix V = matrices[1];
                // 生成推荐列表 (这里可以根据实际需求生成推荐结果)
                List<String> teacherEmailList=selectTeachersByEmail(U,V,users,items,appointment.getStudentEmail());
                List<FreeTime> topFreeTimeList=new ArrayList<>();
                for (String teacherEmail : teacherEmailList) {
                    List<FreeTime> freeTimes = freeTimeService.findByTeacherEmail(teacherEmail);
                    for (FreeTime freeTime : freeTimes) {
                        if (freeTime.getId()==(appointment.getId())||freeTime.isTstatus()) {
                            // 如果当前空闲时间的ID与预约的ID相同，表示这个空闲时间是当前预约的时间段，跳过不添加到推荐列表
                            continue;
                        } else {
                            // 否则将空闲时间添加到推荐列表中
                            topFreeTimeList.add(freeTime);

                        }
                    }
                }
                return Result.success(topFreeTimeList);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.setStatus(401);
            return Result.error("未登录");
        }
    }
    private List<String> selectTeachersByEmail(RealMatrix U, RealMatrix V,List<String> users, List<String> items,String email) {
        // 计算用户对所有老师的评分
        Map<String, Double> teacherRatings = new HashMap<>();
        int userIndex = users.indexOf(email);
        for (int itemIndex = 0; itemIndex < items.size(); itemIndex++) {
            double rating = U.getRowVector(userIndex).dotProduct(V.getColumnVector(itemIndex));
            teacherRatings.put(items.get(itemIndex), rating);
        }

        // 按评分降序排序老师邮箱
        List<Map.Entry<String, Double>> sortedTeachers = new ArrayList<>(teacherRatings.entrySet());
        sortedTeachers.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // 取前十名老师邮箱
        List<String> topTeachers = new ArrayList<>();
        for (int i = 0; i < Math.min(10, sortedTeachers.size()); i++) {
            topTeachers.add(sortedTeachers.get(i).getKey());
        }
        return topTeachers;
    }
    private RealMatrix[] runUVDecomposition(List<Rating> ratings,List<String> users,List<String> items) {
        Map<Pair, Float> M = createSparseMatrix(ratings);
        int numFactors = 3;
        int numIterations = 20;
        double learningRate = 0.01;

        RealMatrix U = MatrixUtils.createRealMatrix(users.size(), numFactors);
        RealMatrix V = MatrixUtils.createRealMatrix(numFactors, items.size());
        initUV(U, V);
        double initialRmse = computeRMSE(M, U, V, users, items);
        System.out.println("intial RMSE: " + initialRmse);

        for (int iter = 0; iter < numIterations; iter++) {
            updateUV(M, U, V, users, items, numFactors, learningRate);
            double rmse = computeRMSE(M, U, V, users, items);
            System.out.println("Iteration " + (iter + 1) + " RMSE: " + rmse);
        }
        return new RealMatrix[]{U, V};
    }
//返回真实稀疏矩阵
    private Map<Pair, Float> createSparseMatrix(List<Rating> ratings) {
        Map<Pair, Float> M = new ConcurrentHashMap<>();
        for (Rating rating : ratings) {
            Pair pair = new Pair(rating.getStudentEmail(), rating.getTeacherEmail());
            M.put(pair, (float) rating.getRating());
        }
        return M;
    }

    private void initUV(RealMatrix U, RealMatrix V) {
        Random random = new Random();
        for (int i = 0; i < U.getRowDimension(); i++) {
            for (int j = 0; j < U.getColumnDimension(); j++) {
                U.setEntry(i, j, random.nextFloat());
            }
        }
        for (int i = 0; i < V.getRowDimension(); i++) {
            for (int j = 0; j < V.getColumnDimension(); j++) {
                V.setEntry(i, j, random.nextFloat());
            }
        }
    }

    private double computeRMSE(Map<Pair, Float> M, RealMatrix U, RealMatrix V, List<String> users, List<String> items) {
        double squareSum = 0;
        for (Map.Entry<Pair, Float> entry : M.entrySet()) {
            int userIndex = users.indexOf(entry.getKey().getUser());
            int itemIndex = items.indexOf(entry.getKey().getItem());
            double predicted = U.getRowVector(userIndex).dotProduct(V.getColumnVector(itemIndex));
            double error = entry.getValue() - predicted;
            squareSum += error * error;
        }
        return Math.sqrt(squareSum / M.size());
    }

    private void updateUV(Map<Pair, Float> M, RealMatrix U, RealMatrix V, List<String> users, List<String> items, int numFactors, double learningRate) {
        for (Map.Entry<Pair, Float> entry : M.entrySet()) {
            int userIndex = users.indexOf(entry.getKey().getUser());
            int itemIndex = items.indexOf(entry.getKey().getItem());
            float actual = entry.getValue();
            RealVector u = U.getRowVector(userIndex);
            RealVector v = V.getColumnVector(itemIndex);
            double predicted = u.dotProduct(v);
            double error = actual - predicted;
            for (int k = 0; k < numFactors; k++) {
                float uVal = (float) U.getEntry(userIndex, k);
                float vVal = (float) V.getEntry(k, itemIndex);
                U.setEntry(userIndex, k, uVal + learningRate * 2 * error * vVal);
                V.setEntry(k, itemIndex, vVal + learningRate * 2 * error * uVal);
            }
        }
    }

    static class Pair {
        private final String user;
        private final String item;

        Pair(String user, String item) {
            this.user = user;
            this.item = item;
        }

        public String getUser() {
            return user;
        }

        public String getItem() {
            return item;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return Objects.equals(user, pair.user) && Objects.equals(item, pair.item);
        }
        @Override
        public int hashCode() {
            return Objects.hash(user, item);
        }
    }
}
