//package com.example;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//public class exercise2 {
//    public static void main(String[] args) {
//        try {
//            // Step 1: 获取所有 deptId
//            String deptIdsUrl = "http://homepage.hit.edu.cn/sysBrowseShow/executeBrowseAllOfSchoolDepart.do";
//            Map<String, List<String>> deptUserInfoMap = new HashMap<>();
//            List<String> deptIds = getDeptIds(deptIdsUrl);
//
//            // Step 2: 对每个 deptId 发送请求并保存用户信息
//            for (String deptId : deptIds) {
//                List<String> userInfoList = getUserInfoByDeptId(deptId);
//                deptUserInfoMap.put(deptId, userInfoList);
//            }
//
//            // 输出保存的用户信息
//            for (Map.Entry<String, List<String>> entry : deptUserInfoMap.entrySet()) {
//                String deptId = entry.getKey();
//                List<String> userInfoList = entry.getValue();
////                System.out.println("Dept ID: " + deptId);
////                System.out.println("User Info: " + userInfoList);
//
//                for (String userInfo : userInfoList) {
//                    String userDetail = getUserDetail(userInfo);
//                    extractTeacherInfo(userDetail);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 获取所有 deptId
//    public static List<String> getDeptIds(String url) throws Exception {
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
//        con.setRequestProperty("Accept-Encoding", "gzip, deflate");
//        con.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
//        con.setRequestProperty("Connection", "keep-alive");
//        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//        con.setRequestProperty("Cookie", "JSESSIONID=53015FACCE2C3F936F8BC2D57147BBD1");
//        con.setRequestProperty("Host", "homepage.hit.edu.cn");
//        con.setRequestProperty("Origin", "http://homepage.hit.edu.cn");
//        con.setRequestProperty("Referer", "http://homepage.hit.edu.cn/school-dept?id=1&browseName=%25E6%25A0%25A1%25E5%2586%2585%25E5%258D%2595%25E4%25BD%258D&browseEnName=DEPARTMENT");
//        con.setRequestProperty("User-Agent", "Mozilla/5.0");
//        con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
//
//        con.setDoOutput(true);
//        String postParams = "";
//        try (OutputStream os = con.getOutputStream()) {
//            byte[] input = postParams.getBytes("utf-8");
//            os.write(input, 0, input.length);
//        }
//
//        int responseCode = con.getResponseCode();
////        System.out.println("POST 响应码:" + responseCode);
//
//        List<String> deptIds = new ArrayList<>();
//
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
//                String inputLine;
//                StringBuilder response = new StringBuilder();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                // 解析JSON响应
//                JSONObject jsonResponse = new JSONObject(response.toString());
//                JSONArray deptList = jsonResponse.getJSONArray("list");
//                for (int i = 0; i < deptList.length(); i++) {
//                    JSONObject dept = deptList.getJSONObject(i);
//                    deptIds.add(dept.getString("id"));
//                }
//            }
//        } else {
//            System.out.println("POST 请求未成功");
//        }
//
//        return deptIds;
//    }
//
//    // 根据 deptId 获取用户信息
//    public static List<String> getUserInfoByDeptId(String deptId) throws Exception {
//        String url = "http://homepage.hit.edu.cn/sysBrowseShow/getUserInfoByDeptId.do";
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//        // 设置基本的POST请求
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
//        con.setRequestProperty("Accept-Encoding", "gzip, deflate");
//        con.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
//        con.setRequestProperty("Connection", "keep-alive");
//        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
//        con.setRequestProperty("Host", "homepage.hit.edu.cn");
//        con.setRequestProperty("Origin", "http://homepage.hit.edu.cn");
//        con.setRequestProperty("Referer", "http://homepage.hit.edu.cn/school-dept?id=1&browseName=%25E6%25A0%25A1%25E5%2586%2585%25E5%258D%2595%25E4%25BD%258D&browseEnName=DEPARTMENT");
//        con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:126.0) Gecko/20100101 Firefox/126.0");
//        con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
//
//        // 构造 POST 请求参数
//        String id = "1"; // 你的 id 参数值
//        String orderType = "1"; // 你的 orderType 参数值
//        String postParams = "deptId=" + deptId + "&id=" + id + "&orderType=" + orderType;
//        // 发送POST请求
//        con.setDoOutput(true);
//        try (OutputStream os = con.getOutputStream()) {
//            byte[] input = postParams.getBytes("utf-8");
//            os.write(input, 0, input.length);
//        }
//        int responseCode = con.getResponseCode();
////        System.out.println("POST 响应码 :" + responseCode);
//
//        List<String> userInfoList = new ArrayList<>();
//
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
//                String inputLine;
//                StringBuilder response = new StringBuilder();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//
//                // 解析JSON响应
//                JSONObject jsonResponse = new JSONObject(response.toString());
//                JSONArray userList = jsonResponse.getJSONArray("list");
//                JSONObject one = userList.getJSONObject(0);
//                // 获取学院名
//                String department = one.getString("department");
////                System.out.println("id:" + deptId + "学院名：" + department);
//                for (int i = 0; i < userList.length(); i++) {
//                    JSONObject user = userList.getJSONObject(i);
//                    userInfoList.add(user.toString());
//                }
//            }
//        } else {
//            System.out.println("POST 请求未成功");
//        }
//        return userInfoList;
//    }
//
//    // 根据用户信息中的 url 获取用户详细信息
//    public static String getUserDetail(String userInfo) throws Exception {
//        JSONObject userJson = new JSONObject(userInfo);
//
//        // Check if url field exists and is not null
//        if (!userJson.has("url") || userJson.isNull("url")) {
//            return "No URL available for this user.";
//        }
//
//        String url = "http://homepage.hit.edu.cn/" + userJson.getString("url");
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//        con.setRequestMethod("GET");
//        int responseCode = con.getResponseCode();
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
//                StringBuilder response = new StringBuilder();
//                String inputLine;
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                // 解析 HTML 并提取详细信息
//                return response.toString();
//            }
//        } else {
//            return "Error getting user detail. Response code: " + responseCode;
//        }
//    }
//
//    public static void extractTeacherInfo(String htmlContent) {
//        // 加载本地HTML文件
//        Document doc = Jsoup.parse(htmlContent, "UTF-8");
////        // 提取个人信息
////        String name = doc.select("h3.tit.chineseName").text();
////        String country = doc.select("em.user-country").text();
////        String position = doc.select("em.user-post").text();
////        String description = doc.select("span.user-describe").attr("title");
////        String department = doc.select("span.user-position").attr("title");
////        String researchAreas = doc.select("span.user-label").attr("title");
////        // 初始化默认值
////        String phone = "N/A";
////        String email = "N/A";
////        String address = "N/A";
////        String homepage = "N/A";
////        Elements contactInfo = doc.select("li.addr");
////        if (contactInfo.size() > 0) {
////            phone = contactInfo.get(0).select("div p").text();
////        }
////        if (contactInfo.size() > 2) {
////            email = contactInfo.get(2).select("div p").text();
////            email = new StringBuilder(email).reverse().toString(); // 反转电子邮件地址
////        }
////        if (contactInfo.size() > 3) {
////            address = contactInfo.get(3).select("div p").text();
////        }
////
////        Elements homepageElements = doc.select("span.user-url");
////        if (!homepageElements.isEmpty()) {
////            homepage = homepageElements.text();
////        }
////        // 打印提取的信息
////        System.out.println("姓名: " + name);
////        System.out.println("国家: " + country);
////        System.out.println("职位: " + position);
////        System.out.println("描述: " + description);
////        System.out.println("部门: " + department);
////        System.out.println("研究方向: " + researchAreas);
////        System.out.println("电话: " + phone);
////        System.out.println("邮箱: " + email);
////        System.out.println("地址: " + address);
////        System.out.println("主页: " + homepage);
//        // 提取个人信息
//        String name = doc.select("h3.tit.chineseName").text();
//        String country = doc.select("em.user-country").text();
//        String position = doc.select("em.user-post").text();
//        String description = doc.select("span.user-describe").attr("title");
//        String department = doc.select("span.user-position").attr("title");
//
//        // 提取研究方向
//        String researchAreas = "";
//        Elements researchAreasElements = doc.select("span.user-label");
//        for (Element element : researchAreasElements) {
//            researchAreas += element.attr("title") + "；";
//        }
//        if (researchAreas.endsWith("；")) {
//            researchAreas = researchAreas.substring(0, researchAreas.length() - 1); // 去掉最后一个分号
//        }
//
//        // 提取图片URL
//        String relativeImageUrl = doc.select("div.pic.user-img img").attr("data-src");
//        String baseUrl = "http://homepage.hit.edu.cn/";
//        String imageUrl = baseUrl + relativeImageUrl;
//
//        // 初始化默认值
//        String phone = "N/A";
//        String email = "N/A";
//        String address = "N/A";
//        String homepage = "N/A";
//
//        Elements contactInfo = doc.select("li.addr");
//        if (contactInfo.size() > 0) {
//            phone = contactInfo.get(0).select("div p").text();
//        }
//        if (contactInfo.size() > 2) {
//            email = contactInfo.get(2).select("div p").text();
//            email = new StringBuilder(email).reverse().toString(); // 反转电子邮件地址
//        }
//        if (contactInfo.size() > 3) {
//            address = contactInfo.get(3).select("div p").text();
//        }
//
//        Elements homepageElements = doc.select("span.user-url");
//        if (!homepageElements.isEmpty()) {
//            homepage = homepageElements.text();
//        }
//
//        // 打印提取的信息
//        System.out.println("姓名: " + name);
//        System.out.println("国家: " + country);
//        System.out.println("职位: " + position);
//        System.out.println("描述: " + description);
//        System.out.println("部门: " + department);
//        System.out.println("研究方向: " + researchAreas);
//        System.out.println("电话: " + phone);
//        System.out.println("邮箱: " + email);
//        System.out.println("地址: " + address);
//        System.out.println("主页: " + homepage);
//        System.out.println("图片URL: " + imageUrl);
//
//
//    }
//}
