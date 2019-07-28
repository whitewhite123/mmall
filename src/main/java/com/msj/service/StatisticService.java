package com.msj.service;

import com.msj.common.ServerResponse;

import javax.servlet.http.HttpSession;

public interface StatisticService {
    ServerResponse baseCount(HttpSession session);
}
