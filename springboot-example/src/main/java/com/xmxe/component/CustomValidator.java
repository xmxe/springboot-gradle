package com.xmxe.component;

import com.xmxe.entity.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * 自定义校验器
 */
public class CustomValidator implements Validator {

	/**
	 * 判断校验类
	 */
	@Override
	public boolean supports(Class<?> clazz) {
//		父类.class.isAssignableFrom(子类.class)
//		子类实例 instanceof 父类类型
		return User.class.isAssignableFrom(clazz);
	}

	/**
	 * 校验方式
	 */
	@Override
	public void validate(Object target, Errors errors) {
		User user = (User)target;
		// 方式1
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"name","","name is required");
		// 方式2
		if (user.getAge() < 18){
			errors.rejectValue("age","age < 18");
		}
	}
}
