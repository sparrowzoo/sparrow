package com.sparrow.support.mobile;

import com.sparrow.constant.Config;
import com.sparrow.constant.ConfigKeyLanguage;
import com.sparrow.constant.SparrowError;
import com.sparrow.core.Pair;
import com.sparrow.cryptogram.ThreeDES;
import com.sparrow.protocol.BusinessException;
import com.sparrow.protocol.MobileShortMessaging;
import com.sparrow.protocol.constant.magic.SYMBOL;
import com.sparrow.utility.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author: zh_harry@163.com
 * @date: 2019-04-06 12:28
 * @description:
 */
public abstract class AbstractShortMessagingService implements ShortMessageService {

    private Logger logger = LoggerFactory.getLogger(AbstractShortMessagingService.class);

    @Override public MobileShortMessaging.Builder init(String mobile) {
        return new MobileShortMessaging.Builder()
                .companyName(com.sparrow.utility.Config.getLanguageValue(ConfigKeyLanguage.MOBILE_COMPANY))
                .key(com.sparrow.utility.Config.getValue(Config.MOBILE_KEY))
                .mobile(mobile)
                .templateId(com.sparrow.utility.Config.getValue(Config.MOBILE_TEMPLATE_ID));
    }


    /**
     * 验证码是否有效
     *
     * @param sendTime send time
     * @param business show label
     * @return 验证码是否有效
     */
    public Boolean valid(Long sendTime, String business) throws BusinessException {
        //手机验证码有效时间
        int mobileValidateTokenAvailableTime = com.sparrow.utility.Config
                .getIntegerValue(Config.MOBILE_VALIDATE_TOKEN_AVAILABLE_TIME);
        Long currentTime = System.currentTimeMillis();
        Long validTime = sendTime + mobileValidateTokenAvailableTime * 1000;
        if (currentTime > validTime) {
            throw new BusinessException(SparrowError.USER_VALIDATE_TIME_OUT, business);
        }
        return true;
    }

    /**
     * 验证码是否正确
     *
     * @param validateCode   current validate code
     * @param shortMessaging short message entity
     * @return 是否验证成功
     */
    @Override public Boolean validate(String validateCode, MobileShortMessaging shortMessaging) throws BusinessException {
        if (com.sparrow.utility.Config.getBooleanValue(Config.DEBUG,false)) {
            return true;
        }
        if (StringUtility.isNullOrEmpty(validateCode)) {
            throw new BusinessException(SparrowError.GLOBAL_PARAMETER_NULL, shortMessaging.getBusiness());
        }
        Boolean result = valid(shortMessaging.getSendTime(), shortMessaging.getBusiness());
        if (!result) {
            return result;
        }
        if (!validateCode.equals(shortMessaging.getValidateCode())) {
            throw new BusinessException(SparrowError.GLOBAL_VALIDATE_CODE_ERROR, shortMessaging.getBusiness());
        }
        return true;
    }

    @Override public MobileShortMessaging.Builder validateCode(MobileShortMessaging.Builder builder) {
        Random random = new Random();
        Integer code = 100000 + random.nextInt(89999);
        builder.validateCode(code.toString());
        builder.sendTime(System.currentTimeMillis());
        String content = String.format("#code#=%1$s&#company#=%2$s", code, builder.getCompanyName());
        builder.content(content);
        logger.debug(content);
        return builder;
    }

    @Override public Pair<String, String> secretMobile(String mobile) {
        if (StringUtility.isNullOrEmpty(mobile)) {
            return Pair.create(SYMBOL.EMPTY, SYMBOL.EMPTY);
        }
        if(mobile.length()<11||!StringUtility.isNumeric(mobile)){
            return Pair.create(SYMBOL.EMPTY, SYMBOL.EMPTY);
        }
        String firstSegment = mobile.substring(0, 3);
        String secondSegment = mobile.substring(3, 7);
        String thirdSegment = mobile.substring(7);

        mobile = firstSegment + "****" + thirdSegment;
        String secretMobile = ThreeDES.getInstance().encrypt(secondSegment, com.sparrow.utility.Config.getValue(Config.MOBILE_SECRET_3DAS_KEY));
        return Pair.create(mobile, secretMobile);
    }
}
