//package com.team.gallexiv.data.api.users;
//
//import cn.hutool.http.HttpStatus;
//import com.team.gallexiv.data.model.UserDao;
//import com.team.gallexiv.data.model.Userinfo;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//public class AvatarController {
//    final UserDao userD;
//
//    public AvatarController(UserDao userD){
//        this.userD = userD;
//    }
//
//
////    public String showAvatar(Model model){
////        List<Userinfo> userAvatar = userD.findAll();
////
////        model.addAttribute((""))
////    }
//    public ResponseEntity<byte[]> downloadAvatar(@RequestParam("userId") Integer userId){
//        Optional<Userinfo> optionalUserinfo = userD.findByUserId(userId);
//
//        if(optionalUserinfo.isEmpty()){
//            return null;
//        }
//
//        Userinfo user = optionalUserinfo.get();
//        byte[] avatar = user.getAvatar();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.ALL);
//
//        return new ResponseEntity<byte[]>(avatar,headers, HttpStatus.HTTP_OK);
//    }
//
//}
