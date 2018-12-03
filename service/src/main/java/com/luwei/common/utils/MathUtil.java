package com.luwei.common.utils;

/**
 * <p> 提供精确的加减乘除运算
 *
 * @author Leone
 * @since 2018-09-03
 **/
public class MathUtil {


    /**
     * 计算打折
     *
     * @param source
     * @param discount
     * @return
     */
    public static Integer discount(Integer source, Integer discount) {
        if (discount <= 10 && discount >= 1) {
            Double var1 = Double.parseDouble(source.toString());
            Double var2 = Double.parseDouble(discount.toString()) / 10;
            Double result = (var1 * var2);
            return result.intValue();
        } else {
            throw new RuntimeException("折扣数有误");
        }

    }


    public static void main(String[] args) {
        System.out.println(discount(3000, 110));
    }

}
