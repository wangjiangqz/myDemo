package com.mytest.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式测试demo
 * author 超帅大将哥
 */
@RunWith(SpringJUnit4ClassRunner.class) /*添加SpringJUnit支持，引入Spring-Test框架*/
@SpringBootTest(classes = MyTest.class) /*指定Springboot启动类启动*/
public class RegexTest
{
    /**
     * 先记一些基本类型匹配知识
     * .    匹配除换行符外任意字符
     * \w   匹配字母活数字或下划线或汉字
     * \s   匹配任意的空白符
     * \d   匹配数字
     * \b   匹配单词的开始或者结束
     * ^    匹配字符串的开始
     * $    匹配字符串的结束
     *
     * 再记一些表示数量的重复限定符
     * *    重复0次或更多次
     * +    重复1次或更多次
     * ?    重复0次或1次
     * {n}  重复n次
     * {n,} 重复n次或更多次
     * {n,m}重复n到m次
     *
     *再记一些拓展
     * 举个例子：^(ab)* 通过小括号把ab当做一个整体
     * 如果字符串中包含小括号，需要转义
     * 条件或通过 | 来实现
     *
     * 区间
     * 限定0-9 ：[0-9]
     * 限定A-Z ： [A-Z]
     * 限定某些数字 ： [169] = [1,6,9]
     *
     */

/*----------------------------------------------------------------------*/

    /**
     * 正则的进阶知识
     * 1、零宽断言
     *  ①正向先行断言
     *      (?=pattern)
     *      匹配pattern表达式的前面内容，不返回本身。
     *  ②正向后行断言
     *      (?<=pattern)
     *      匹配pattern表达式的后面的内容，不返回本身。
     *  ③负向先行断言
     *      (?!pattern)
     *      匹配非pattern表达式的前面内容，不返回本身。
     *  ④负向后行断言
     *      (?<!pattern)
     *      匹配非pattern表达式的后面内容，不返回本身。
     *
     * 2、捕获组
     *  ①数字编号捕获组
     *      (exp)
     *      从表达式左侧开始，每出现一个左括号和它对应的右括号之间的内容为一个分组，在分组中，第0组为整个表达式，第一组开始为分组。
     *  ②命名编号捕获组
     *      (?<name>exp)
     *      分组的命名由表达式中的name指定
     *      比如区号也可以这样写:(?<fenzu1>\0\d{2})-(?<fenzu2>\d{8})
     *  ③非捕获组
     *      (?:exp)
     *      和捕获组刚好相反，它用来标识那些不需要捕获的分组，说的通俗一点，就是你可以根据需要去保存你的分组。
     *
     * 3、反向引用
     *  ①数字编号组反向引用：\k或\number
     *  ②命名编号组反向引用：\k或者\'name'
     *
     * 4、贪婪与反贪婪
     *      普通重复限定词为贪婪量词。
     *      懒惰量词是在贪婪量词后面加?
     *      *?  重复任意次，但尽可能少重复
     *      +?  重复1次或更多次，但尽可能少重复
     *      ??  重复0次或1次，但尽可能少重复
     *      {n,m}?  重复n到m次，但尽可能少重复
     *      {n,}?   重复n次以上，但尽可能少重复
     *
     * 5、反义
     *  \W  匹配任意不是字母，数字，下划线，汉字的字符
     *  \S  匹配任意不是空白符的字符
     *  \D  匹配任意非数字的字符
     *  \B  匹配不是单词开头或结尾的位置
     *  [^x]    匹配除了x以外的任意字符
     *  [^aeiou]    匹配除了aeiou这几个字母之外的任意字符
     *
     */

/*----------------------------------------------------------------------*/

    /**
     * 测试匹配联通手机号码
     */
    @Test
    public void testMobile(){
        String reg = "^((13[0-2])|(15[56])|(18[5-6])|145|176)\\d{8}$";
        String mobileNumber = "13142929561";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(mobileNumber);
        System.out.println(matcher.matches());
    }

    /**
     * 测试前断言
     */
    @Test
    public void testQdy(){
        String reg = "\\d+(?=</span>)";
        String test = "<span class=\"read-count\">阅读数：641</span>";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(test);
        while (matcher.find()){
            System.out.printf("匹配结果：%s",matcher.group());
        }
    }

    /**
     * 测试后断言
     */
    @Test
    public void testHdy(){
        String reg = "(?<=<span class=\"read-count\">阅读数：)\\d+";
        String test = "<span class=\"read-count\">阅读数：641</span>";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(test);
        while (matcher.find()){
            System.out.printf("匹配结果：%s",matcher.group());
        }
    }

    /**
     * 测试数字捕获组
     */
    @Test
    public void testGroupNumber(){
        String mobile = "020-86532648";
        String reg = "^(0\\d{2})-(\\d{8})$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(mobile);
        if (matcher.find()){
            System.out.println("分组的个数有：" + matcher.groupCount());
            for (int i=0;i <= matcher.groupCount();i++){
                System.out.printf("第 %d 个分组内容为： %s %s",i,matcher.group(i),"\n");
            }
        }
    }

    /**
     * 测试命名捕获组
     */
    @Test
    public void testGroupName(){
        String mobile = "020-86532648";
        String reg = "^(?<fenzu1>0\\d{2})-(?<fenzu2>\\d{8})$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(mobile);
        if (matcher.find()){
            System.out.println("分组的个数有：" + matcher.groupCount());
            System.out.println("fenzu1内容：" + matcher.group("fenzu1"));
            System.out.println("fenzu2内容：" + matcher.group("fenzu2"));
        }
    }

    /**
     * 测试非捕获组
     */
    @Test
    public void testGroupNo(){
        String mobile = "020-86532648";
        String reg="(?:0\\d{2})-(\\d{8})";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(mobile);
        if (matcher.find()){
            System.out.println("分组的个数有：" + matcher.groupCount());
            for (int i=0;i <= matcher.groupCount();i++){
                System.out.printf("第 %d 个分组内容为： %s %s",i,matcher.group(i),"\n");
            }
        }
    }

    /**
     * 测试反向引用之查找所有的连续相同的字母
     */
    @Test
    public void testSame(){
        String test = "aabbbbgbddesddfiid";
        Pattern pattern = Pattern.compile("(\\w)\\1");
        Matcher matcher = pattern.matcher(test);
        while (matcher.find()){
            System.out.println(matcher.group());
        }
    }

    /**
     * 测试反向引用之替换
     */
    @Test
    public void testReplace(){
        String test = "abcbbabcbcgbddesddfiid";
        String reg = "(a)(b)c";
        System.out.println(test.replaceAll(reg,"$1"));
    }

    /**
     * 测试贪婪
     */
    @Test
    public void testTl(){
        String test="61762828 176 2991 87321";
        String reg="(\\d{1,2})(\\d{3,4})";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(test);
        while (matcher.find()){
            System.out.println("匹配结果：" + matcher.group(0));
        }
    }

    /**
     * 测试反贪婪
     */
    @Test
    public void testTlNo(){
        String test="61762828 176 2991 87321";
        String reg="(\\d{1,2}?)(\\d{3,4})";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(test);
        while (matcher.find()){
            System.out.println("匹配结果：" + matcher.group(0));
        }
    }

}
