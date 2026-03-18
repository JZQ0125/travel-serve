package com.project.travel.controller.ai;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.project.travel.domain.Result;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * AI 控制器 - 豆包真实 API
 */
@RestController
@RequestMapping("/ai")
public class AIController {

    // 豆包 API 配置 - 使用你的真实配置
    private static final String DOUBAO_API_KEY = "2dbb20a5-b655-4fa6-9300-a08621c6d8f5";
    private static final String DOUBAO_ENDPOINT = "ep-20260306001831-rwb2w";
    private static final String DOUBAO_API_URL = "https://ark.cn-beijing.volces.com/api/v3/chat/completions";

    // 系统提示词
    private static final String SYSTEM_PROMPT = "你是一个专业的旅游助手，专门为用户提供旅游相关的建议和帮助。你的职责包括：\n\n" +
            "1. 推荐旅游景点、酒店和旅游线路\n" +
            "2. 解答旅游相关的问题（如交通、住宿、美食、天气、最佳旅游时间等）\n" +
            "3. 提供旅游攻略和注意事项\n" +
            "4. 帮助用户规划行程和预算\n" +
            "5. 介绍当地特色美食和文化\n\n" +
            "请用友好、专业的语气回答问题，并尽可能提供实用的建议。回答要简洁明了，重点突出。";

    @PostMapping("/chat")
    public Result chat(@RequestBody JSONObject request) {
        try {
            System.out.println("=== 收到 AI 请求 ===");
            System.out.println("请求内容：" + request.toJSONString());
            
            // 获取消息历史
            JSONArray messages = request.getJSONArray("messages");
            
            // 添加系统提示词
            JSONArray messagesWithSystem = new JSONArray();
            JSONObject systemMessage = new JSONObject();
            systemMessage.put("role", "system");
            systemMessage.put("content", SYSTEM_PROMPT);
            messagesWithSystem.add(systemMessage);
            messagesWithSystem.addAll(messages);

            // 构建请求体
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", DOUBAO_ENDPOINT);
            requestBody.put("messages", messagesWithSystem);
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 2000);

            System.out.println("=== 调用豆包 API ===");
            System.out.println("API URL: " + DOUBAO_API_URL);
            System.out.println("端点 ID: " + DOUBAO_ENDPOINT);
            System.out.println("API Key: " + DOUBAO_API_KEY.substring(0, 8) + "...");
            System.out.println("请求体: " + requestBody.toJSONString());

            // 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + DOUBAO_API_KEY);

            // 发送请求
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = new HttpEntity<>(requestBody.toJSONString(), headers);
            
            ResponseEntity<String> response = restTemplate.exchange(
                    DOUBAO_API_URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            System.out.println("=== 豆包 API 响应 ===");
            System.out.println("状态码: " + response.getStatusCode());
            System.out.println("响应体: " + response.getBody());

            // 解析响应
            JSONObject responseBody = JSONObject.parseObject(response.getBody());
            
            return Result.success(responseBody);
            
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            System.err.println("=== HTTP 错误 ===");
            System.err.println("状态码: " + e.getStatusCode());
            System.err.println("错误信息: " + e.getMessage());
            System.err.println("响应体: " + e.getResponseBodyAsString());
            
            String errorMsg = "AI 服务调用失败";
            if (e.getStatusCode().value() == 401) {
                errorMsg = "API Key 认证失败。请检查：\n1. API Key 是否正确\n2. API Key 是否已激活\n3. 是否有调用该端点的权限";
            } else if (e.getStatusCode().value() == 404) {
                errorMsg = "端点不存在。请检查端点 ID 是否正确：" + DOUBAO_ENDPOINT;
            } else if (e.getStatusCode().value() == 429) {
                errorMsg = "API 调用次数超限，请稍后再试";
            } else if (e.getStatusCode().value() == 403) {
                errorMsg = "没有权限访问该端点。请在豆包控制台检查 API Key 的权限配置";
            }
            
            return Result.fail(errorMsg);
            
        } catch (Exception e) {
            System.err.println("=== 其他错误 ===");
            e.printStackTrace();
            return Result.fail("AI 服务调用失败：" + e.getMessage());
        }
    }
}
