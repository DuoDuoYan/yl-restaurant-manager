package com.yan.open.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ImageUtil {

    public static String upload(HttpServletRequest request, MultipartFile multipartFile)throws IOException{
        String image = null;
        //上传图片
        if(multipartFile != null && !multipartFile.isEmpty()){
            //使用UUID命名图片
            String name = UUID.randomUUID().toString().replaceAll("-","");
            //获取文件扩展名
            String suffix = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            //设置图片上传路径
            String path = request.getSession().getServletContext().getRealPath("\\upload");

            String imagePath = name + "." + suffix;
            //检验文件夹是否存在
            if(isFileExists(path)){
                //以绝对路径保存重名名后的图片
                multipartFile.transferTo(new File(path + "\\" + name + "." + suffix));
                //装配图片地址
                return imagePath;
            }
        }
        return null;
    }

    /**
     * 验证文件夹是否存在
     * @param path
     */
    public static Boolean isFileExists(String path){
        File file = new File(path);
        if(!file.exists()){
            if (!file.mkdir()){
                return true;
            }else{
                return false;
            }
        }
        return true;
    }

    /**
     * 删除图片
     * @param photo
     */
    public static void deleteFile(String photo,HttpServletRequest request){
        if(photo.length()>0){
            String path = request.getSession().getServletContext().getRealPath("\\upload");
            String image = photo.substring(photo.lastIndexOf("\\"));
            File file = new File(path + image);
            if(isFileExists(path + image)){
                file.delete();
            }
        }
    }

    public static File getFile(String image){

        return null;
    }

    public static List<File> getFiles(String image){

        return null;
    }

}
