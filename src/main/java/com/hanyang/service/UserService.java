package com.hanyang.service;

import com.hanyang.dutychain.AbstractBusinessHandler;
import com.hanyang.dutychain.CityHandler;
import com.hanyang.dutychain.builder.HandlerEnum;
import com.hanyang.pojo.BusinessLaunch;
import com.hanyang.pojo.UserInfo;
import com.hanyang.repo.BusinessLaunchRepository;
import com.hanyang.repo.UserRepository;
import com.hanyang.ticket.proxy.DirectorProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessLaunchRepository businessLaunchRepository;

    @Autowired
    private DirectorProxy directorProxy;

    @Value("${duty.chain}")
    private String handlerType;
    private String currentHandlerType;
    private AbstractBusinessHandler currentHandler;

    public String login(String account, String password) {
        UserInfo userInfo = userRepository.findByUserNameAndUserPassword(account, password);
        if (userInfo == null) {
            return "account / password ERROR!";
        }
        return "Login Success";
    }

    public String register(UserInfo userInfo) {
        if (checkUserExists(userInfo.getUserName())) {
            throw new RuntimeException("User already registered.");
        }
        userInfo.setCreateDate(new Date());

        userRepository.save(userInfo);
        return "Register Success!";
    }

    public boolean checkUserExists(String userName) {
        UserInfo user = userRepository.findByUserName(userName);
        if (user == null) {
            return false;
        }
        return true;
    }

    public List<BusinessLaunch> filterBusinessLaunch(String city, String sex, String product) {
        List<BusinessLaunch> launchList = businessLaunchRepository.findAll();
        return buildChain().processHandler(launchList, city, sex, product);
    }

    public Object createTicket(String type, String productId, String content, String title, String bankInfo, String taxId) {
        return directorProxy.buildTicket(type, productId, content, title, bankInfo, taxId);
    }

    // 组装责任链条并返回责任链条首节点
    private AbstractBusinessHandler buildChain() {
        if (handlerType == null) {
            return null;
        }
        // 如果第一次配置，将handlerType记录
        if (currentHandlerType == null) {
            this.currentHandlerType = this.handlerType;
        }
        if (this.handlerType.equals(currentHandler) && this.currentHandler != null) {
            return this.currentHandler;
        } else {
            System.out.println("配置有修改或首次初始化，组装责任链条！！！");
            synchronized (this) {
                try {
                    AbstractBusinessHandler dummyHeadHandler = new CityHandler();
                    AbstractBusinessHandler preHandler = dummyHeadHandler;
                    List<String> handlerTypeList = Arrays.asList(handlerType.split(","));
                    for (String handlerType : handlerTypeList) {
                        AbstractBusinessHandler handler = (AbstractBusinessHandler) Class.forName(HandlerEnum.valueOf(handlerType).getValue()).newInstance();
                        preHandler.nextHandler = handler;
                        preHandler = handler;
                    }
                    // 重新赋值新的责任链头节点
                    this.currentHandler = dummyHeadHandler.nextHandler;
                    // 重新赋值修改后的配置
                    this.currentHandlerType = this.handlerType;
                    // 返回责任链头节点
                    return currentHandler;
                } catch (Exception e) {
                    throw new UnsupportedOperationException(e);
                }
            }
        }
    }

}
