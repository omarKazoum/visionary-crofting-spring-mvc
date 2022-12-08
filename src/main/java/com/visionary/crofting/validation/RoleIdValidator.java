package com.visionary.crofting.validation;

import com.visionary.crofting.entity.RoleEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RoleIdValidator implements ConstraintValidator<ValidRoleId,Long> {
    @Override
    public boolean isValid(Long roleId, ConstraintValidatorContext constraintValidatorContext) {
        return roleId>=0 && roleId< RoleEnum.values().length;
    }
}
