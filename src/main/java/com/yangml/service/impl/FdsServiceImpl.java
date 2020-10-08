package com.yangml.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangml.dao.FdsMapper;
import com.yangml.pojo.Fds;
import com.yangml.service.FdsService;
import org.springframework.stereotype.Service;

@Service
public class FdsServiceImpl extends ServiceImpl<FdsMapper, Fds> implements FdsService {
}
