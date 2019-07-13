package com.msj.controller.backend;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msj.common.ManageConst;
import com.msj.common.ServerResponse;
import com.msj.pojo.Product;
import com.msj.pojo.User;
import com.msj.service.portal.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private ProductService productService;

    /**
     * 查看产品list
     * @param pageNum
     * @param pageSize
     * @param session
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "pageNum",defaultValue ="1")Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                               HttpSession session){
        User user = (User)session.getAttribute("user");
        if(user!=null){
            PageHelper.startPage(pageNum,pageSize);
            List<Product> productList = productService.getProductList();
            PageInfo<Product> productPageInfo = new PageInfo<Product>(productList);
            return ServerResponse.createSuccess(productPageInfo);

        }
        return ServerResponse.createErrorByMessage(ManageConst.GETPRODUCT_ERROR);
    }

    /**
     * 产品搜素
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/search.do")
    @ResponseBody
    public ServerResponse search(@RequestParam("productName")String productName,
                                 @RequestParam("productId") Integer productId,
                                 @RequestParam(value = "pageNum",defaultValue ="1")Integer pageNum,
                                 @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        //todo 根据产品的productName和productId进行搜素
        return null;
    }

    @RequestMapping("/upload.do")
    @ResponseBody
    public ServerResponse upload(){
        //todo 图片上传
        return null;
    }

    @RequestMapping("/detail.do")
    @ResponseBody
    public ServerResponse detail(Integer productId){
        Product productDetail = productService.getProductDetail(productId);
        if(productDetail!=null){
            return ServerResponse.createSuccess(productDetail);
        }
        return ServerResponse.createErrorByMessage(ManageConst.GETLIST_ERROR);
    }

}
