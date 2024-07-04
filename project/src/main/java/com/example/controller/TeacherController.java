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
import java.io.DataOutputStream;
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
                        Teacher teacher = extractTeacherInfoAndSave(userDetail);
                        String introduce = getMoreTeacherNews(userInfo);
                        teacher.setBasicinfo(introduce);
                        if(teacher.getEmail() != null && !teacher.getEmail().isEmpty() &&
                                teacher.getName() != null && !teacher.getName().isEmpty() &&
                                teacher.getDepartment() != null && !teacher.getDepartment().isEmpty()) {
//                        System.out.println(teacher);
                            saveTeacherToDatabase(teacher);
                        }
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

        // 根据用户信息获取更多教师新闻
        public static String getMoreTeacherNews(String userInfo) throws Exception {
            JSONObject userJson = new JSONObject(userInfo);

            if (!userJson.has("id") || userJson.isNull("id")) {
                return "No id available for this user.";
            }
            String id = userJson.getString("id");
            String url = "http://homepage.hit.edu.cn/TeacherHome/teacherBody.do";
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
            con.setRequestProperty("Accept-Encoding", "gzip, deflate");
            con.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Connection", "keep-alive");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            con.setRequestProperty("Cookie", "JSESSIONID=1CEC39416049D2907906377A89DD53F0");
            con.setRequestProperty("Host", "homepage.hit.edu.cn");
            con.setRequestProperty("Origin", "http://homepage.hit.edu.cn");
            con.setRequestProperty("Pragma", "no-cache");
            con.setRequestProperty("Referer", "http://homepage.hit.edu.cn/dengzongquang?lang=zh");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36 SLBrowser/9.0.3.5211 SLBChan/103");
            con.setRequestProperty("X-Requested-With", "XMLHttpRequest");

            String urlParameters = "id=" + id + "&versionId=";

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(urlParameters);
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            System.out.println("响应状态码 : " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    String resp = response.toString().replaceAll("\\\\", "");
                    //System.out.println(resp);
                    Document doc = Jsoup.parse(resp);
                    Elements editorContentDivs = doc.select("div.tacherContentParts" +
                            "> div.part_box:first-child " +
                            "> ul.tacherContent_parts:first-child " +
                            "> li:first-child " +
                            "> dl" +
                            "> ol" +
                            "> div:not(:first-child)");
                    //System.out.println(editorContentDivs);
                    StringBuilder result = new StringBuilder();
// 遍历每个 div 元素，并提取其文本内容
                    for (Element div : editorContentDivs) {

                        String content = div.text(); // 获取 div 元素的文本内容
                        result.append(content).append("\n"); // 将文本内容添加到 StringBuilder 中，每个内容换行
                    }
                    System.out.println(result);
                    return result.toString(); // 返回合并后的文本内容字符串
                }
            } else {
                System.out.println("请求失败");
                return null;
            }
        }

        // 提取教师信息并保存到数据库
        public static Teacher extractTeacherInfoAndSave(String htmlContent) {
            // 加载本地HTML文件
            Document doc = Jsoup.parse(htmlContent, "UTF-8");
            // 提取个人信息
            String name = "";
            String title = "";
            String description = "";
            String department = "";
            String country = "";
            String researchArea = "";
            String imageUrl = "";
            String phone = "";
            String email = "";
            String officeAddress = "";
            String homepage = "";

            Element nameElement = doc.select("h3.tit.chineseName").first();
            if (nameElement != null) {
                name = nameElement.text();
            }

            Element titleElement = doc.select("em.user-post").first();
            if (titleElement != null) {
                title = titleElement.text();
            }

            Element descriptionElement = doc.select("span.user-describe").first();
            if (descriptionElement != null) {
                description = descriptionElement.attr("title");
            }

            Element departmentElement = doc.select("span.user-position").first();
            if (departmentElement != null) {
                department = departmentElement.attr("title");
            }

            Element countryElement = doc.selectFirst("em.user-country");
            if (countryElement != null) {
                country = countryElement.text();
            }

// 提取研究方向
            Elements researchAreasElements = doc.select("span.user-label");
            for (Element element : researchAreasElements) {
                researchArea += element.attr("title") + "；";
            }
            if (researchArea.endsWith("；")) {
                researchArea = researchArea.substring(0, researchArea.length() - 1); // 去掉最后一个分号
            }

// 提取图片URL
            Element imageElement = doc.select("div.pic.user-img img").first();
            if (imageElement != null) {
                String relativeImageUrl = imageElement.attr("data-src");
                String baseUrl = "http://homepage.hit.edu.cn/";
                imageUrl = baseUrl + relativeImageUrl;
            }

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
            teacher.setCountry(country);
            return teacher;

        }

        // 将教师信息保存到数据库
        public static void saveTeacherToDatabase(Teacher teacher) {
            String sql = "INSERT INTO teacher (name, department, title, email, researchArea, researchResults, " +
                    "officeAddress, phone, imageUrl, description, homepage, country, basicinfo) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE " +
                    "name = VALUES(name), " +
                    "department = VALUES(department), " +
                    "title = VALUES(title), " +
                    "researchArea = VALUES(researchArea), " +
                    "researchResults = VALUES(researchResults), " +
                    "officeAddress = VALUES(officeAddress), " +
                    "phone = VALUES(phone), " +
                    "imageUrl = VALUES(imageUrl), " +
                    "description = VALUES(description), " +
                    "homepage = VALUES(homepage), " +
                    "country = VALUES(country), " +
                    "basicinfo = VALUES(basicinfo)";
            String userPasswordSql = "INSERT INTO userpassword (email, password, isTeacher) VALUES (?, ?, 1) " +
                    "ON DUPLICATE KEY UPDATE password = VALUES(password)";
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://154.8.150.128:3306/softwareyong", "root", "123456");
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 PreparedStatement userPasswordStmt = conn.prepareStatement(userPasswordSql)) {

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
                pstmt.setString(13, teacher.getBasicinfo());
                // 执行插入操作
                pstmt.executeUpdate();
                System.out.println("Teacher information saved to database successfully!");
                // 设置userpassword表参数
                userPasswordStmt.setString(1, teacher.getEmail());
                userPasswordStmt.setString(2, "123456"); // 设置默认密码为123456

                // 执行userpassword表插入操作
                userPasswordStmt.executeUpdate();
                System.out.println("Default password inserted into userpassword table successfully!");
            } catch (SQLException e) {
                e.printStackTrace(); // 这里可以考虑记录日志或者返回给用户一个错误信息
            }
        }
}
