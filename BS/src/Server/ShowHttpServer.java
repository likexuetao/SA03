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

public class ShowHttpServer implements HttpHandler {


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
        ServerData serverData = new ServerData();

        StringBuilder response = new StringBuilder();
        response.append("<html><body><h1>通讯录</h1>");
        response.append("<table border='1'><tr><th>姓名</th><th>地址</th><th>电话</th></tr>"); // 因为我不确定你的数据列数和列名，所以这里我暂时设置为“未知”

        for (int i = 0; i < serverData.peopleList.size(); i++) {
            String[] personData = serverData.peopleList.get(i).toString().split("\t\t"); // 按制表符分割每一行数据
            response.append("<tr>");

            for (String data : personData) {
                response.append("<td>").append(data).append("</td>"); // 将每一列数据添加到表格单元格中
            }

            response.append("</tr>");
        }

        response.append("</table></body></html>");

        // 编码为 UTF-8 并发送到客户端
        byte[] bytes = response.toString().getBytes(StandardCharsets.UTF_8); // 设置字符编码为 UTF-8
        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8"); // 添加字符编码标头
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(bytes);
        outputStream.close();
    }
}
