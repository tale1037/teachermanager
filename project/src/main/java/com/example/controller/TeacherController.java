package com.example.controller;

import com.example.mapper.UserPasswordMapper;
import com.example.pojo.Result;
import com.example.pojo.Student;
import com.example.pojo.Teacher;
import com.example.service.Impl.*;
import com.example.utils.JwtUtil;
import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/teachers")
    public Result findall(){
        List<Teacher> teacherList=teacherService.findAll();
        return Result.success(teacherList);
    }
    @GetMapping("/teachers/{email}")
    public Result findByEmail(@PathVariable String email){
        Teacher teacher=teacherService.findByEmail(email);
        return Result.success(teacher);
    }
    @PostMapping("/teachers/delete")
    public Result deleteByEmail(@RequestBody Teacher teacher){
        teacherService.deleteByEmail(teacher.getEmail());

        return Result.success();
    }
    @PostMapping("/teachers")
    public Result insert(@RequestBody Teacher teacher){
        System.out.println("enter!");
        teacherService.insert(teacher);
        return Result.success();
    }
    @PutMapping("/teachers")
    public Result update(@RequestBody Teacher teacher){
        teacherService.update(teacher);
        return Result.success();
    }
    // 根据教师部门查询教师信息
    @GetMapping("/teachers/department/{department}")
    public Result findBydepartment(@PathVariable String department) {
        List<Teacher> teacherList=teacherService.findByDepartment(department);
        return Result.success(teacherList);
    }
    @GetMapping("/teachers/name/{name}")
    public Result findByName(@PathVariable String name) {
        List<Teacher> teacherList=teacherService.findByName(name);
        return Result.success(teacherList);
    }
    // 获取所有教师的部门选项
    @GetMapping("teachers/departments")
    public Result findAllDepartments() {
        List<String> stringList=teacherService.findAllDepartments();
        return Result.success(stringList);
    }
    @GetMapping("/youselfnews")
    public Result yourInfo(@RequestHeader(name="Authorization",required = false) String token, HttpServletResponse response){
        try {
            System.out.println(1);
            Map<String,Object> claims = JwtUtil.parseToken(token);
            String email = (String)claims.get("email");
            System.out.println(claims.toString());
            boolean isteacher = (boolean)claims.get("isteacher");
            if(isteacher) {
                return Result.success(teacherService.findByEmail(email));
            }
            else{
                System.out.println("enter this");
                Student student = studentService.findStudentByEmail(email);
                System.out.println(student.toString());
                return Result.success(studentService.findStudentByEmail(email));
            }
            //return Response.success(teachers);
        }catch (Exception e){
            response.setStatus(401);
            return Result.error("weidenglu");
        }
    }
    @GetMapping("/teacher/getdetail")
    public Result getinfobyemail(@RequestHeader(name="Authorization",required = false) String token, HttpServletResponse response,@RequestParam String email){
        try {
            System.out.println(1111);
                return Result.success(teacherService.findByEmail(email));
            //return Response.success(teachers);
        }catch (Exception e){
            response.setStatus(401);
            System.out.println(e.getMessage());
            return Result.error("weidenglu");
        }
    }
    @GetMapping("/teacher/getlist")
    public Result findAll(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestParam(required = false) String inputname,@RequestParam(required = false) String inputDepartment) {
        if(inputname==null||inputname.equals("")) {
            if(inputDepartment==null||inputDepartment.equals("")) {
                System.out.println("teacher_name==null");
                return Result.success(teacherService.findList(pageNum, pageSize));
            }
            else{
                return Result.success(teacherService.findListbyDepartment(pageNum, pageSize , inputDepartment));
            }
        }
        else{
            System.out.println("teacher_name=="+inputname);
            return Result.success(teacherService.findList(pageNum,pageSize,inputname));
        }
        //return Result.success(teacherList);
    }
    @GetMapping("/updateteachers")
    void getnews(){
        try {
            // Step 1: 获取所有 deptId
            String deptIdsUrl = "http://homepage.hit.edu.cn/sysBrowseShow/executeBrowseAllOfSchoolDepart.do";
            Map<String, List<String>> deptUserInfoMap = new HashMap<>();
            List<String> deptIds = getDeptIds(deptIdsUrl);

            // Step 2: 对每个 deptId 发送请求并保存用户信息
            for (String deptId : deptIds) {
                List<String> userInfoList = getUserInfoByDeptId(deptId);
                deptUserInfoMap.put(deptId, userInfoList);
            }

            // 输出保存的用户信息
            for (Map.Entry<String, List<String>> entry : deptUserInfoMap.entrySet()) {
                String deptId = entry.getKey();
                List<String> userInfoList = entry.getValue();

                for (String userInfo : userInfoList) {
                    String userDetail = getUserDetail(userInfo);
                    extractTeacherInfoAndSave(userDetail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取所有 deptId
    public static List<String> getDeptIds(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        con.setRequestProperty("Accept-Encoding", "gzip, deflate");
        con.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        con.setRequestProperty("Connection", "keep-alive");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        con.setRequestProperty("Cookie", "JSESSIONID=53015FACCE2C3F936F8BC2D57147BBD1");
        con.setRequestProperty("Host", "homepage.hit.edu.cn");
        con.setRequestProperty("Origin", "http://homepage.hit.edu.cn");
        con.setRequestProperty("Referer", "http://homepage.hit.edu.cn/school-dept?id=1&browseName=%25E6%25A0%25A1%25E5%2586%2585%25E5%258D%2595%25E4%25BD%258D&browseEnName=DEPARTMENT");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("X-Requested-With", "XMLHttpRequest");

        con.setDoOutput(true);
        String postParams = "";
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = postParams.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = con.getResponseCode();
        List<String> deptIds = new ArrayList<>();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                // 解析JSON响应
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray deptList = jsonResponse.getJSONArray("list");
                for (int i = 0; i < deptList.length(); i++) {
                    JSONObject dept = deptList.getJSONObject(i);
                    deptIds.add(dept.getString("id"));
                }
            }
        } else {
            System.out.println("POST 请求未成功");
        }

        return deptIds;
    }

    // 根据 deptId 获取用户信息
    public static List<String> getUserInfoByDeptId(String deptId) throws Exception {
        String url = "http://homepage.hit.edu.cn/sysBrowseShow/getUserInfoByDeptId.do";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // 设置基本的POST请求
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
        con.setRequestProperty("Accept-Encoding", "gzip, deflate");
        con.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
        con.setRequestProperty("Connection", "keep-alive");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        con.setRequestProperty("Host", "homepage.hit.edu.cn");
        con.setRequestProperty("Origin", "http://homepage.hit.edu.cn");
        con.setRequestProperty("Referer", "http://homepage.hit.edu.cn/school-dept?id=1&browseName=%25E6%25A0%25A1%25E5%2586%2585%25E5%258D%2595%25E4%25BD%258D&browseEnName=DEPARTMENT");
        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:126.0) Gecko/20100101 Firefox/126.0");
        con.setRequestProperty("X-Requested-With", "XMLHttpRequest");

        // 构造 POST 请求参数
        String id = "1"; // 你的 id 参数值
        String orderType = "1"; // 你的 orderType 参数值
        String postParams = "deptId=" + deptId + "&id=" + id + "&orderType=" + orderType;
        // 发送POST请求
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = postParams.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        int responseCode = con.getResponseCode();

        List<String> userInfoList = new ArrayList<>();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // 解析JSON响应
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray userList = jsonResponse.getJSONArray("list");
                for (int i = 0; i < userList.length(); i++) {
                    JSONObject user = userList.getJSONObject(i);
                    userInfoList.add(user.toString());
                }
            }
        } else {
            System.out.println("POST 请求未成功");
        }
        return userInfoList;
    }

    // 根据用户信息中的 url 获取用户详细信息
    public static String getUserDetail(String userInfo) throws Exception {
        JSONObject userJson = new JSONObject(userInfo);

        // Check if url field exists and is not null
        if (!userJson.has("url") || userJson.isNull("url")) {
            return "No URL available for this user.";
        }

        String url = "http://homepage.hit.edu.cn/" + userJson.getString("url");
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                // 解析 HTML 并提取详细信息
                return response.toString();
            }
        } else {
            return "Error getting user detail. Response code: " + responseCode;
        }
    }

    // 提取教师信息并保存到数据库
    public static void extractTeacherInfoAndSave(String htmlContent) {
        // 加载本地HTML文件
        Document doc = Jsoup.parse(htmlContent, "UTF-8");
        // 提取个人信息
        String name = doc.select("h3.tit.chineseName").text();
        String title = doc.select("em.user-post").text();
        String description = doc.select("span.user-describe").attr("title");
        String department = doc.select("span.user-position").attr("title");
        String country = doc.select("em.user-country").attr("title");
        // 提取研究方向
        String researchArea = "";
        Elements researchAreasElements = doc.select("span.user-label");
        for (Element element : researchAreasElements) {
            researchArea += element.attr("title") + "；";
        }
        if (researchArea.endsWith("；")) {
            researchArea = researchArea.substring(0, researchArea.length() - 1); // 去掉最后一个分号
        }

        // 提取图片URL
        String relativeImageUrl = doc.select("div.pic.user-img img").attr("data-src");
        String baseUrl = "http://homepage.hit.edu.cn/";
        String imageUrl = baseUrl + relativeImageUrl;

        // 初始化默认值
        String phone = "N/A";
        String email = "N/A";
        String  officeAddress= "N/A";
        String homepage = "N/A";

        Elements contactInfo = doc.select("li.addr");
        if (contactInfo.size() > 0) {
            phone = contactInfo.get(0).select("div p").text();
        }
        if (contactInfo.size() > 2) {
            email = contactInfo.get(2).select("div p").text();
            email = new StringBuilder(email).reverse().toString(); // 反转电子邮件地址
        }
        if (contactInfo.size() > 3) {
            officeAddress = contactInfo.get(3).select("div p").text();
        }

        Elements homepageElements = doc.select("span.user-url");
        if (!homepageElements.isEmpty()) {
            homepage = homepageElements.text();
        }

        // 创建 Teacher 对象
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setDepartment(department);
        teacher.setTitle(title);
        teacher.setEmail(email);
        teacher.setResearchArea(researchArea);
        teacher.setResearchResults("");
        teacher.setOfficeAddress(officeAddress);
        teacher.setPhone(phone);
        teacher.setImageUrl(imageUrl);
        teacher.setDescription(description);
        teacher.setHomepage(homepage);

        // 将教师信息保存到数据库
        saveTeacherToDatabase(teacher);
    }

    // 将教师信息保存到数据库
    public static void saveTeacherToDatabase(Teacher teacher) {
        String sql = "INSERT INTO teacher (name, department, title, email, researchArea, researchResults, " +
                "officeAddress, phone, imageUrl, description, homepage,country) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://154.8.150.128:3306/softwareyong", "root", "123456");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 设置参数
            pstmt.setString(1, teacher.getName());
            pstmt.setString(2, teacher.getDepartment());
            pstmt.setString(3, teacher.getTitle());
            pstmt.setString(4, teacher.getEmail());
            pstmt.setString(5, teacher.getResearchArea());
            pstmt.setString(6, teacher.getResearchResults());
            pstmt.setString(7, teacher.getOfficeAddress());
            pstmt.setString(8, teacher.getPhone());
            pstmt.setString(9, teacher.getImageUrl());
            pstmt.setString(10, teacher.getDescription());
            pstmt.setString(11, teacher.getHomepage());
            pstmt.setString(12, teacher.getCountry());
            // 执行插入操作
            pstmt.executeUpdate();
            System.out.println("Teacher information saved to database successfully!");
        } catch (SQLException e) {
            e.printStackTrace(); // 这里可以考虑记录日志或者返回给用户一个错误信息
        }
    }
}
