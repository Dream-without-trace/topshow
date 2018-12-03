import com.luwei.WebApplication;
import com.luwei.models.activity.Activity;
import com.luwei.models.activity.ActivityDao;
import com.luwei.models.collect.CollectDao;
import com.luwei.services.collect.CollectService;
import com.luwei.services.goods.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-14
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
public class AllTest {

    @Resource
    private CollectService collectService;

    @Resource
    private CollectDao collectDao;

    @Resource
    private GoodsService goodsService;

    @Resource
    private ActivityDao activityDao;

    @Test
    public void test() {
//        List<Collect> collects = collectDao.findCollectsByIdAndType(2, CollectType.GOODS);
//        System.out.println(collects.size());
//        System.out.println(collects);
//        List<Integer> goodsIds = goodsService.findSoldOutGoods(SalesStatus.OFF);
//        System.out.println(goodsIds);
//        List<Activity> list = activityDao.findActivityByStartTimeLessThan(new Date());
//        System.out.println(list);
    }

}
