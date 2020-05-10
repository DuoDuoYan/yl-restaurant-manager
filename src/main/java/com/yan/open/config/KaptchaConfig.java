package com.yan.open.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;
import com.google.code.kaptcha.util.Config;

@Configuration
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        com.google.code.kaptcha.impl.DefaultKaptcha defaultKaptcha = new com.google.code.kaptcha.impl.DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", "red");
        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "35");
        properties.setProperty("kaptcha.background.clear.from", "white");
        properties.setProperty("kaptcha.background.clear.to", "white");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "微软雅黑");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }
}
//        kaptcha.producer.impl 图片实现类 com.google.code.kaptcha.impl.DefaultKaptcha
//        kaptcha.textproducer.impl 文本实现类 com.google.code.kaptcha.text.impl.DefaultTextCreator
//        kaptcha.textproducer.char.string 文本集合，验证码值从此集合中获取 abcde2345678gfynmnpwx
//        kaptcha.textproducer.char.space 文字间隔 2
//        kaptcha.noise.impl 干扰实现类 com.google.code.kaptcha.impl.DefaultNoise
//        kaptcha.noise.color 干扰颜色，合法值： r,g,b 或者 white,black,blue. black
//        kaptcha.obscurificator.impl 图片样式： 水纹com.google.code.kaptcha.impl.WaterRipple 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy 阴影com.google.code.kaptcha.impl.ShadowGimpy com.google.code.kaptcha.impl.WaterRipple
//        kaptcha.background.impl 背景实现类 com.google.code.kaptcha.impl.DefaultBackground
//        kaptcha.word.impl 文字渲染器 com.google.code.kaptcha.text.impl.DefaultWordRenderer
//        kaptcha.session.key session key KAPTCHA_SESSION_KEY
//        kaptcha.session.date session date KAPTCHA_SESSION_DATE
