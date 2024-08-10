package com.yupi.springbootinit.job.cycle;


import com.yupi.springbootinit.mapper.PostMapper;
import com.yupi.springbootinit.model.entity.Post;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.collection.CollUtil;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 增量同步帖子到 es
 *

 */
// todo 取消注释开启任务
//@Component
@Slf4j
public class IncSyncPostToEs {

    @Resource
    private PostMapper postMapper;

}
