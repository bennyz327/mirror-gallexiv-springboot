// package com.team.gallexiv.security.ctrl;

// import com.team.gallexiv.common.lang.VueData;
// import com.team.gallexiv.data.model.UserService;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// public class VerifyTest extends BaseController {

// private final UserService userS;

// public VerifyTest(UserService userS) {
// this.userS = userS;
// }

// @GetMapping("/test/admin")
// public String admin() {
// return "admin OK";
// }
// @GetMapping("/test/user")
// public String user() {
// return "user OK";
// }

// //包裝測試
// @GetMapping("/test/userall")
// public VueData findAllUser() {
// return VueData.ok(userS.getAllUsers());
// }

// //redis測試
// @GetMapping("/test/redis")
// public VueData redisTest() {
// redisUtil.set("redis", "这是redis的测试数据");
// Object redis = redisUtil.get("redis");
// return VueData.ok(redis.toString());
// }

// }
