package com.flipchase.android.service.impl;

import java.util.List;

import com.flipchase.android.domain.Alert;
import com.flipchase.android.service.AlertService;

public class AlertServiceImpl implements AlertService {

	@Override
	public Integer getCountForEmail(Long email) {
		return null;
	}

	@Override
	public Alert findAlertByEmail(Long id, String email) {
		return null;
	}

	@Override
	public List<Alert> findAlertsByEmail(String email) {
		return null;
	}

	@Override
	public Alert findAlertByEmailAndCategory(Long id, Long id2) {
		return null;
	}

	@Override
	public Alert findAlertByEmailAndRetailer(Long id, Long id2) {
		return null;
	}

}
