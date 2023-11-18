package Server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class EditHttpServer implements HttpHandler {
    private ServerData serverData = new ServerData();
    private static Map<String, String> queryToMap(String query) throws UnsupportedEncodingException {
        Map<String, String> result = new HashMap<>();
        if (query != null) {
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], URLDecoder.decode(entry[1], StandardCharsets.UTF_8));
                } else {
                    result.put(entry[0], "");
                }
            }
        }
        return result;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 获取查询字符串
        String queryString = exchange.getRequestURI().getQuery();

        // 解析查询参数
        Map<String, String> queryParams = queryToMap(queryString);

        // 从解析后的参数中获取表单字段的值
        String name1 = queryParams.get("name1");
        String name2 = queryParams.get("name2");
        String address = queryParams.get("address");
        String phone = queryParams.get("phoneNumber");

        //查找 并删除name1
        People people1 = null;
        for (People value : serverData.peopleList) {
            if (name1.equals(value.getName())) {
                people1 = value;
            }
        }
        if (people1 == null){
            System.out.println("查无此人!!!");
        }
        serverData.peopleList.remove(people1);

        // 处理表单数据
        People people = new People(name2,address,phone);
        serverData.peopleList.add(people);
        try{
            serverData.writePeopleListToFile(serverData.peopleList);
        }catch (Exception e){
            e.printStackTrace();
        }

        // 返回响应给前端
        String response = "编辑成功";
        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        outputStream.close();
    }
}
