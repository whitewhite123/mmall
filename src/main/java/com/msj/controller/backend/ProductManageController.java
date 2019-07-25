package com.msj.controller.backend;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msj.common.ManageConst;
import com.msj.common.ServerResponse;
import com.msj.pojo.Product;
import com.msj.pojo.User;
import com.msj.service.ProductService;
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

    //产品list
    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "pageNum",defaultValue ="1")Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                               HttpSession session){
        return productService.getList(pageNum,pageSize,session);
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

    /**
     * 产品详情
     * @param productId
     * @return
     */
    @RequestMapping("/detail.do")
    @ResponseBody
    public ServerResponse detail(Integer productId){
//        Product productDetail = productService.getProductDetail(productId);
//        if(productDetail!=null){
//            return ServerResponse.createSuccess(productDetail);
//        }
//        return ServerResponse.createByErrorMessage(ManageConst.GETLIST_ERROR);
        return null;
    }

    /**
     * 产品上下架
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping("/set_sale_status.do")
    @ResponseBody
    public ServerResponse setStatus(Integer productId,Integer status){
        int num = productService.editProductStatus(productId, status);
        if(num>0){
            return ServerResponse.createBySuccessMessage(ManageConst.UPDATE_STATUS_SUCCESS);
        }
        return ServerResponse.createByErrorMessage(ManageConst.UPDATE_STATUS_ERROR);
    }

    /**
     * 新增OR更新产品
     * @param product
     * @return
     */
    @RequestMapping("/save.do")
    @ResponseBody
    public ServerResponse save(Product product){
        //todo 新增OR更新产品出错
        Integer id = product.getId();
        System.out.println(id);
        Product pro = productService.getProductById(id);
        System.out.println(pro);
        if(pro!=null){
            int num = productService.editProduct(pro);
            System.out.println("更新"+num);
            if(num>0){
                return ServerResponse.createBySuccessMessage(ManageConst.UPDATE_STATUS_SUCCESS);
            }
        }
        int num = productService.addProduct(product);
        System.out.println("新增"+num);
        if(num>0){
            return ServerResponse.createBySuccessMessage(ManageConst.INSERT_PRODUCT_SUCCESS);
        }
        return ServerResponse.createByErrorMessage(ManageConst.UPDATE_STATUS_ERROR);


    }

}
