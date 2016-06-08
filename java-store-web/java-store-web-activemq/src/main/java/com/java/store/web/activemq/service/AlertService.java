package com.java.store.web.activemq.service;

import com.java.store.web.activemq.entity.Spittle;

/**
 * @author zeronx
 * @date 2016年6月2日下午9:51:48
 * @version v1.0
 */
public interface AlertService {

	void sendSpitterAlert(Spittle spittle);
}
