package com.onlinechat;

import com.github.binarywang.java.emoji.EmojiConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName : EmojiConverterTest
 * @Description :
 * @Author : y
 * @Date: 2021-11-03 21:34
 */


public class EmojiConverterTest {

    private EmojiConverter emojiConverter = EmojiConverter.getInstance();

    @Test
    public void testToAlias() {
        String str = "  An 😃😀awesome 😃😃string with a few 😃😉emojis!";
        String alias = this.emojiConverter.toAlias(str);
        System.out.println(str);
        System.out.println("EmojiConverterTest.testToAlias()=====>");
        System.out.println(alias);

    }
}
