package com.project.travel.controller.system;

import com.project.travel.domain.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

/**
 * 图片导出工具
 */
@RestController
@RequestMapping("/system")
public class ImageExportController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/export-images")
    public Result exportImages() {
        System.out.println("========================================");
        System.out.println("开始导出图片...");
        System.out.println("========================================");
        
        try {
            String baseDir = System.getProperty("user.dir") + File.separator + "img" + File.separator;
            System.out.println("图片目录: " + baseDir);
            
            File imgDir = new File(baseDir);
            if (!imgDir.exists()) {
                imgDir.mkdirs();
                System.out.println("创建图片目录成功");
            }

            int count = 0;

            // 导出轮播图
            System.out.println("\n=== 开始导出轮播图 ===");
            List<Map<String, Object>> rotations = jdbcTemplate.queryForList(
                    "SELECT id, image FROM sys_rotations"
            );
            System.out.println("找到 " + rotations.size() + " 条轮播图记录");
            
            for (Map<String, Object> row : rotations) {
                String id = row.get("id").toString();
                Object imageObj = row.get("image");
                
                // 检查是否已经是路径
                if (imageObj instanceof String) {
                    System.out.println("轮播图 " + id + " 已经是路径: " + imageObj);
                    continue;
                }
                
                byte[] imageData = (byte[]) imageObj;
                if (imageData != null && imageData.length > 0) {
                    String fileName = "rotation_" + id + ".jpg";
                    saveImage(baseDir + fileName, imageData);
                    // 更新数据库路径
                    jdbcTemplate.update(
                            "UPDATE sys_rotations SET image = ? WHERE id = ?",
                            "/img/" + fileName, id
                    );
                    count++;
                    System.out.println("✓ 导出轮播图: " + fileName + " (" + imageData.length + " bytes)");
                }
            }

            // 导出景点图片
            System.out.println("\n=== 开始导出景点图片 ===");
            List<Map<String, Object>> attractions = jdbcTemplate.queryForList(
                    "SELECT id, images FROM sys_attractions"
            );
            System.out.println("找到 " + attractions.size() + " 条景点记录");
            
            for (Map<String, Object> row : attractions) {
                String id = row.get("id").toString();
                Object imageObj = row.get("images");
                
                // 检查是否已经是路径
                if (imageObj instanceof String) {
                    System.out.println("景点 " + id + " 已经是路径: " + imageObj);
                    continue;
                }
                
                byte[] imageData = (byte[]) imageObj;
                if (imageData != null && imageData.length > 0) {
                    String fileName = "attraction_" + id + ".jpg";
                    saveImage(baseDir + fileName, imageData);
                    // 更新数据库路径
                    jdbcTemplate.update(
                            "UPDATE sys_attractions SET images = ? WHERE id = ?",
                            "/img/" + fileName, id
                    );
                    count++;
                    System.out.println("✓ 导出景点图片: " + fileName + " (" + imageData.length + " bytes)");
                }
            }

            // 导出线路图片
            System.out.println("\n=== 开始导出线路图片 ===");
            List<Map<String, Object>> lines = jdbcTemplate.queryForList(
                    "SELECT id, images FROM sys_line"
            );
            System.out.println("找到 " + lines.size() + " 条线路记录");
            
            for (Map<String, Object> row : lines) {
                String id = row.get("id").toString();
                Object imageObj = row.get("images");
                
                // 检查是否已经是路径
                if (imageObj instanceof String) {
                    System.out.println("线路 " + id + " 已经是路径: " + imageObj);
                    continue;
                }
                
                byte[] imageData = (byte[]) imageObj;
                if (imageData != null && imageData.length > 0) {
                    String fileName = "line_" + id + ".jpg";
                    saveImage(baseDir + fileName, imageData);
                    // 更新数据库路径
                    jdbcTemplate.update(
                            "UPDATE sys_line SET images = ? WHERE id = ?",
                            "/img/" + fileName, id
                    );
                    count++;
                    System.out.println("✓ 导出线路图片: " + fileName + " (" + imageData.length + " bytes)");
                }
            }

            // 导出用户头像
            System.out.println("\n=== 开始导出用户头像 ===");
            List<Map<String, Object>> users = jdbcTemplate.queryForList(
                    "SELECT id, avatar FROM user WHERE avatar IS NOT NULL"
            );
            System.out.println("找到 " + users.size() + " 条用户记录");
            
            for (Map<String, Object> row : users) {
                String id = row.get("id").toString();
                Object imageObj = row.get("avatar");
                
                // 检查是否已经是路径
                if (imageObj instanceof String) {
                    System.out.println("用户 " + id + " 已经是路径: " + imageObj);
                    continue;
                }
                
                byte[] imageData = (byte[]) imageObj;
                if (imageData != null && imageData.length > 0) {
                    String fileName = "avatar_" + id + ".jpg";
                    saveImage(baseDir + fileName, imageData);
                    // 更新数据库路径
                    jdbcTemplate.update(
                            "UPDATE user SET avatar = ? WHERE id = ?",
                            "/img/" + fileName, id
                    );
                    count++;
                    System.out.println("✓ 导出用户头像: " + fileName + " (" + imageData.length + " bytes)");
                }
            }

            System.out.println("\n========================================");
            if (count == 0) {
                System.out.println("所有图片已经是路径格式，无需导出");
                System.out.println("========================================");
                return Result.success("所有图片已经是路径格式，无需导出。图片目录: " + baseDir);
            } else {
                System.out.println("导出完成！共导出 " + count + " 张图片");
                System.out.println("图片保存在: " + baseDir);
                System.out.println("========================================");
                return Result.success("图片导出成功！共导出 " + count + " 张图片到目录: " + baseDir);
            }

        } catch (Exception e) {
            System.err.println("========================================");
            System.err.println("导出失败！");
            e.printStackTrace();
            System.err.println("========================================");
            return Result.fail("图片导出失败：" + e.getMessage());
        }
    }

    private void saveImage(String filePath, byte[] imageData) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imageData);
        }
    }
}
