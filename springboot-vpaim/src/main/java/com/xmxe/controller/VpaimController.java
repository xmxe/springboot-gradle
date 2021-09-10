package com.xmxe.controller;

import com.xmxe.component.CustomCompoenet;
import com.xmxe.component.CustomValidator;
import com.xmxe.component.StringToListPropertyEditor;
import com.xmxe.entity.User;
import com.xmxe.service.VpaimService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class VpaimController {

	private static final Logger logger = LoggerFactory.getLogger(VpaimController.class);

	private final VpaimService vpaimService;

	private CustomCompoenet customCompoenet;

	// 构造器注入保证依赖不可变（final关键字）,保证依赖不为空（省去了我们对其检查）,保证返回客户端（调用）的代码的时候是完全初始化的状态,
	// 避免了循环依赖,提升了代码的可复用性,推荐使用构造器注入，因为类在加载时先加载构造方法后加载@Autowired
	// 不使用构造器注入如果想在构造方法里使用bean的某个方法 会出现NPE异常,因为bean还没有被加载
	@Autowired
	public VpaimController(VpaimService vpaimService){
		this.vpaimService = vpaimService;
	}

	// 方法注入
	@Autowired
	public void methodInjection(CustomCompoenet customCompoenet){
		customCompoenet.setA(2);
		customCompoenet.setB("qw");
		this.customCompoenet = customCompoenet;
	}


	@RequestMapping("hello/{text}")
	public String hello(@PathVariable("text") String text){
		vpaimService.serviceMethod();
		logger.info(customCompoenet.toString());
		return "hello gradle";
	}

	/**
	 * 自定义属性编辑器
	 */
	@GetMapping("binder")// ip:port/binder?date=2020-12-12 10:54:45&text=11_22_33
	public String binder(Date date,String[] text){
		logger.info("date={}",date);
		logger.info("array={}", Arrays.toString(text));
		return "binder";
	}

	@GetMapping("webbinder")
	public String webbinder(String webbinder){
		return webbinder;
	}

	@GetMapping("user")
	public String webbinder(@Validated User user){
		return user.toString();
	}

	/**
	 * InitBinder methods should return void
	 */
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		// 将传过来的参数转换为Date格式
		parseDate(webDataBinder);
		// 带"_"的String转数组
		string2array(webDataBinder);
	}

	/**
	 * 将传过来的参数转换为Date格式
	 */
	private void parseDate(WebDataBinder webDataBinder){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// allowEmpty = true 允许date字段为null
		CustomDateEditor dateEditor = new CustomDateEditor(df, true);
		webDataBinder.registerCustomEditor(Date.class, dateEditor);
	}

	/**
	 * 将参数带"_"的String转数组
	 */
	private void string2array(WebDataBinder webDataBinder){
		webDataBinder.registerCustomEditor(String[].class, new StringToListPropertyEditor());
	}

	@InitBinder(value = {"webbinder","user"})// 若指定了value值，那么只有方法参数名（或者模型名）匹配上了此注解方法才会执行（若不指定，都执行）。
	public void initBinder_(WebDataBinder webDataBinder){
		webDataBinder.setValidator(new CustomValidator());
//		webDataBinder.addValidators();
		// 禁用属性
		webDataBinder.setDisallowedFields("address");
		// 在属性中去除pre_前缀 访问http://localhost:9081/user?pre_name=1&pre_age=19返回age=19 name=1 address=null
		webDataBinder.setFieldDefaultPrefix("pre_");
//		webDataBinder.setFieldMarkerPrefix("mark_");
//		logger.info(webDataBinder.getFieldMarkerPrefix());
//		webDataBinder.addCustomFormatter();

	}

	/*
	 * @ModelAttribute注解用于将方法的参数或方法的返回值绑定到指定的模型属性上，并返回给Web视图
	 * @ModelAttribute注解注释的方法会在此controller每个方法执行前被执行，因此对于一个controller映射多个URL的用法来说，要谨慎使用。
	 */

	/*
	 * @RequestMapping和@ModelAndView同时标注在一个方法上
	 * 这时这个方法的返回值并不是表示一个视图名称，而是model属性的值，视图名称由RequestToViewNameTranslator根据请求"/index"转换为逻辑视图index
	 * Model属性名称由@ModelAttribute("attributeName")指定，相当于在request中封装了key=attributeName，value=index
	 * ...
	 * 如果 @ModelAttribute 和 @RequestMapping 注解在同一个方法上，那么代表给这个请求单独设置 Model 参数。此时返回的值是 Model 的参数值，而不是跳转的地址。跳转的地址是根据请求的 url 自动转换而来的
	 */
	@GetMapping("/index")
	@ModelAttribute("attributeName")
	public String index(){
		// 当使用@RestController时想要返回视图使用ModelAndView
		/*ModelAndView mv = new ModelAndView("index");
		return mv;*/
		return "index";
	}

	/*
	 * @ModelAttribute注释void返回值的方法
	 * 在请求/index后,method_void方法在index方法之前先被调用，它把请求参数（/index?a=abc）加入到一个名为attributeName的model属性中，
	 * 在它执行后method_void被调用，返回视图名index和model已由@ModelAttribute方法生产好了。
	 * 这个例子中model属性名称和model属性对象有model.addAttribute()实现，不过前提是要在方法中加入一个Model类型的参数。
	 * 当URL或者post中不包含参数时，会报错，其实不需要这个方法，完全可以把请求的方法写成下面的样子，这样缺少此参数也不会出错：
	 * @GetMapping(value = "/index")
	 * public ModelAndView index(String a) { return new ModelAndView("index");}
	 */
	@ModelAttribute
	public void method_void(@RequestParam(value = "a",required = false) String a, Model model){
		model.addAttribute("a",a);
	}

	/*
	 * @ModelAttribute注释有返回值的方法
	 * 相当于model.addAttribute("messages",List<Map<String,String>>)
	 */
	@ModelAttribute("messages")
	public List<Map<String,String>> messages() {
//		return List.of("message1","message2");
		return List.of(Map.of("k1","v1","k2","v2"));
	}

	/*
	 * @ModelAttribute注释在参数上说明了该方法参数的值将由model中取得。如果model中找不到，那么该参数会先被实例化，然后被添加到model中。
	 * 在model中存在以后，请求中所有名称匹配的参数都会填充到该参数中。
	 */
	@ModelAttribute("userM")
	public User addAccount() {
		return new User("jz",2);
	}

	@RequestMapping(value = "/userModel")// http://localhost:9081/userModel?a=q 请求路径带上参数a 上面的示例中要求必须带上参数a
	public void userModel(@ModelAttribute("userM") User user) {
		logger.info("user={}",user.toString());

	}

}
