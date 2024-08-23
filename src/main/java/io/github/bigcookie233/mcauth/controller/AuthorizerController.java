package io.github.bigcookie233.mcauth.controller;

import dev.samstevens.totp.code.CodeVerifier;
import io.github.bigcookie233.mcauth.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Base64;

@Controller
public class AuthorizerController {
    private CodeVerifier verifier;
    private String secret;

    @Autowired
    public AuthorizerController(CodeVerifier verifier) {
        this.verifier = verifier;
        this.secret = System.getenv("secret");
        this.secret = this.secret != null ? this.secret : "";
    }

    @GetMapping("code")
    public String code(Model model,
                       @RequestParam String minecraft_id,
                       @RequestParam String redirect,
                       @RequestParam(required = false) String message) {
        model.addAttribute("minecraft_id", minecraft_id);
        model.addAttribute("redirect", redirect);
        if (message != null)
        {
            model.addAttribute("message", new String(Base64.getUrlDecoder().decode(message)));
        }
        return "code";
    }

    @PostMapping("authenticate")
    public RedirectView authenticate(@RequestParam String minecraft_id,
                                     @RequestParam String code,
                                     @RequestParam String redirect) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath("/code")
                .queryParam("minecraft_id", minecraft_id)
                .queryParam("redirect", redirect);
        if (!Utils.validateMinecraftId(minecraft_id)) {
            return new RedirectView(uriComponentsBuilder.queryParam("message", Base64.getUrlEncoder().encodeToString("无效的 Minecraft ID".getBytes())).encode().toUriString());
        }
        if (!Utils.validateCode(code)) {
            return new RedirectView(uriComponentsBuilder.queryParam("message", Base64.getUrlEncoder().encodeToString("无效的验证码".getBytes())).encode().toUriString());
        }
        String playerSecret = Utils.calculateSHA256(this.secret + minecraft_id);
        if (!verifier.isValidCode(playerSecret, code)) {
            return new RedirectView(uriComponentsBuilder.queryParam("message", Base64.getUrlEncoder().encodeToString("验证码错误".getBytes())).encode().toUriString());
        }
        return new RedirectView(redirect);
    }
}
