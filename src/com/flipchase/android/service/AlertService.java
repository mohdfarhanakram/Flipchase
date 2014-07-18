package com.flipchase.android.service;

import java.util.List;

import com.flipchase.android.domain.Alert;


public interface AlertService {

	public Integer getCountForEmail(Long email);

	public Alert findAlertByEmail(Long id, String email);

	public List<Alert> findAlertsByEmail(String email);

	public Alert findAlertByEmailAndCategory(Long id, Long id2);

	public Alert findAlertByEmailAndRetailer(Long id, Long id2);
}
