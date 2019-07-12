package com.cm.controller;

import com.cm.model.Result;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@RequestMapping("file")
@Controller
public class FileController {
    /**
     *   
     *      * 文件上传功能  
     *      * @param file  
     *      * @return  
     *      * @throws IOException   
     *      
     */
    @RequestMapping(value="/upload",produces="text/html;charset=utf-8", method = RequestMethod.POST)
    @ResponseBody
    private String upload2(@RequestParam("file") CommonsMultipartFile partFile, HttpServletRequest request) {
        try {
            String path = request.getServletContext().getRealPath("/upload");
            String name = request.getParameter("name");
            String filename = partFile.getOriginalFilename();
            File file = new File(path+"/"+filename);
            InputStream inputStream = partFile.getInputStream();
            FileUtils.copyInputStreamToFile(inputStream, file);
            if(inputStream!=null){
                inputStream.close();
            }
            return "文件上传成功！";
        } catch (Exception e) {
            e.printStackTrace();
            return "文件上传失败！";
        }
    }
//    /**
//      * 文件下载
//      * 单个文件下载
//      * @param request
//      * @return
//      * @throws IOException
//    */
//    @RequestMapping(value="/down", method = RequestMethod.POST)
//    @ResponseBody
//    public void down(HttpServletRequest request,HttpServletResponse response, String file) throws Exception{
//        //模拟文件，myfile.txt为需要下载的文件
//        String fileName = request.getSession().getServletContext().getRealPath("upload")+'/'+file;
//        //获取输入流
//        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
//        //假如以中文名下载的话
//        String filename = file;
//       //转码，免得文件名中文乱码
//        filename = URLEncoder.encode(filename,"UTF-8");
//       //设置文件下载头
//        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
//       //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
//        response.setContentType("multipart/form-data");
//
//        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
//        int len = 0;
//        while((len = bis.read()) != -1){
//            out.write(len);
//            out.flush();
//        }
//        out.close();
//    }
    /**
     * 文件下载
     * @throws IOException
     */
    @RequestMapping(value="/download",method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<byte[]> downloadHomework(String fileName, HttpServletResponse response, HttpSession session, HttpServletRequest request) throws IOException{
        //指定要下载的文件所在路径
        String path = "F:/projects/SpringMvc/target/SpringMvc/upload/";
        //创建该文件对象
        File file = new File(path+File.separator+fileName);
        //设置响应头
        HttpHeaders headers = new HttpHeaders();
        //通知浏览器以下载的方式打开文件
        headers.setContentDispositionFormData("attachment", fileName);
        //定义以流的方式下载返回文件数据
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK);

    }
    @RequestMapping(value="/uploadImg",method=RequestMethod.POST)
    @ResponseBody
    public Result upload(@RequestParam("picture") MultipartFile picture, HttpServletRequest request) {
        //获取文件在服务器的储存位置
        String path = request.getSession().getServletContext().getRealPath("/uploadImg");
        File filePath = new File(path);
        System.out.println("文件的保存路径：" + path);
        if (!filePath.exists() && !filePath.isDirectory()) {
            System.out.println("目录不存在，创建目录:" + filePath);
            filePath.mkdir();
        }

        //获取原始文件名称(包含格式)
        String originalFileName = picture.getOriginalFilename();
        System.out.println("原始文件名称：" + originalFileName);

        //获取文件类型，以最后一个`.`为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        System.out.println("文件类型：" + type);
        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0, originalFileName.lastIndexOf("."));

        //设置文件新名称: 当前时间+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        String fileName = date + name + "." + type;
        System.out.println("新文件名称：" + fileName);

        //在指定路径下创建一个文件
        File targetFile = new File(path, fileName);

        //将文件保存到服务器指定位置
        try {
            picture.transferTo(targetFile);
            System.out.println("上传成功");
            //将文件在服务器的存储路径返回
            return new Result(true,"/uploadImg/" + fileName);
        } catch (IOException e) {
            System.out.println("上传失败");
            e.printStackTrace();
            return new Result(false, "上传失败");
        }
    }
}