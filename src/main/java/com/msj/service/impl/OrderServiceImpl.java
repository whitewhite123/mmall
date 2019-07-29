package com.msj.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msj.common.Const;
import com.msj.common.ResponseCode;
import com.msj.common.ServerResponse;
import com.msj.mapper.CartMapper;
import com.msj.mapper.OrderItemMapper;
import com.msj.mapper.OrderMapper;
import com.msj.mapper.ProductMapper;
import com.msj.pojo.*;
import com.msj.service.OrderService;
import com.msj.vo.OrderItemVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;


    //创建订单
    public ServerResponse create(Integer shippingId, HttpSession session) {
        //1、判断用户是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.NEED_LOGIN_ERROR.getDesc());//用户未登录
        }
        //2、根据userId和check=1（已勾选）获取productId
        List<Cart> cartList = cartMapper.selectByUidAndChecked(user.getId());
        //3、新增order
        // (order只有一个，orderItem有一个或一个以上，一个订单有一个或多个产品)
        Order order = addOrder(user.getId(),shippingId,cartList);
        int resultCount = orderMapper.addOrder(order);
        if(resultCount<0){
            return ServerResponse.createByErrorMessage(Const.CREATE_ORDER_ERROR);//创建订单失败
        }
        //4、新增orderItemList
        List<OrderItem> orderItemList = addOrderItemList(user.getId(), cartList, order.getOrderNo());
        for(OrderItem orderItem:orderItemList){
            int itemCount = orderItemMapper.addOrderItem(orderItem);
            if(itemCount<0){
                return ServerResponse.createByErrorMessage(Const.CREATE_ORDERITEM_ERROR);//创建订单详情失败
            }
        }
        //5、新增订单后，相应删除购物车cart信息(根据userId和checked)
        cartMapper.deleteByUidAndPid(user.getId());
        //6、根据userId和orderNo查看订单
        order = orderMapper.selectOrderByUidAndOrderNo(user.getId(),order.getOrderNo());
        //7、整合order
        order = assembleOrder(order,user.getId());
        if(order == null){
            return ServerResponse.createByErrorMessage(Const.SELECT_ORDER_ERROR);//没有找到该订单
        }
        return ServerResponse.createSuccess(order);

    }

    //得到order
    private Order addOrder(Integer userId,Integer shippingId,List<Cart> cartList){
        //根据cartList查出payment
        double payment = 0;
        for(Cart c:cartList){
            Integer productId = c.getProductId();
            Product product = productMapper.selectByPrimaryKey(productId);
            Double price = product.getPrice();
            Integer quantity = c.getQuantity();
            Double totalPrice = price*quantity;
            payment = totalPrice + payment;
        }

        Order order = new Order();
        Long orderNo = System.currentTimeMillis();
        order.setOrderNo(BigInteger.valueOf(orderNo));
        order.setUserId(userId);
        order.setPayment(BigDecimal.valueOf(payment));
        order.setPaymentType(1);
        order.setPostage(10);
        order.setStatus(10);
        order.setShippingId(shippingId);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());

        return order;
    }

    //得到orderItem
    private List<OrderItem> addOrderItemList(Integer userId,List<Cart> cartList,BigInteger orderNo){
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        OrderItem orderItem = new OrderItem();
        for(Cart c:cartList){
            orderItem.setUserId(userId);
            orderItem.setOrderNo(orderNo.longValue());
            orderItem.setProductId(c.getProductId());
            Product p = productMapper.selectByPrimaryKey(c.getProductId());
            orderItem.setProductName(p.getName());
            orderItem.setProductImage(p.getMainImage());
            orderItem.setCurrentUnitPrice(BigDecimal.valueOf(p.getPrice()));
            orderItem.setQuantity(c.getQuantity());
            orderItem.setTotalPrice(BigDecimal.valueOf(c.getQuantity()*p.getPrice()));
            orderItem.setCreateTime(new Date());
            orderItem.setUpdateTime(new Date());
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }
    
    //获取订单的商品信息
    public ServerResponse getOrderCartProduct(HttpSession session) {
        //1、判断用户是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.NEED_LOGIN_ERROR.getDesc());//用户未登录
        }
        //2、根据userId获取订单
        List<OrderItem> orderItemList = orderItemMapper.selectOrderItemByUserId(user.getId());
        //3、整合orderItemVo
        OrderItemVo orderItemVo  = assembleOrderItemVo(orderItemList,user.getId());
        if(orderItemVo == null){
            return ServerResponse.createByErrorMessage(Const.SEL_ORDER_ERROR);
        }
        return ServerResponse.createSuccess(orderItemVo);
    }

    //获取订单List
    public ServerResponse getOrderList(HttpSession session,Integer pageSize,Integer pageNum) {
        //1、判断用户是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.NEED_LOGIN_ERROR.getDesc());//用户未登录
        }

        //2、根据userId查询order
        List<Order> orderList = orderMapper.selectOrderByUserId(user.getId());
        //3、整合order
        orderList = assembleOrderList(orderList, user.getId(),user.getUsername());
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Order> pageInfo = new PageInfo<Order>(orderList);
        return ServerResponse.createSuccess(pageInfo);
    }

    //获取订单详情
    public ServerResponse detail(HttpSession session, BigInteger orderNo) {
        //1、判断用户是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.NEED_LOGIN_ERROR.getDesc());//用户未登录
        }
        //2、根据userId和orderNo查看订单
        Order order = orderMapper.selectOrderByUidAndOrderNo(user.getId(),orderNo);
        //3、整合order
        order = assembleOrder(order,user.getId());
        if(order == null){
            return ServerResponse.createByErrorMessage(Const.SELECT_ORDER_ERROR);
        }
        return ServerResponse.createSuccess(order);
    }

    //取消订单
    public ServerResponse cancel(HttpSession session, BigInteger orderNo) {
        //1、判断用户是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.NEED_LOGIN_ERROR.getDesc());//用户未登录
        }
        //2、根据userId和orderNo查询订单
        Order order = orderMapper.selectOrderByUidAndOrderNo(user.getId(), orderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage(Const.NO_ORDER);
        }
        //3、更改订单状态
        if(order.getStatus() >= 20 || order.getStatus() == 0){//已付款
            return ServerResponse.createByErrorMessage(Const.CANCEL_ORDER_ERROR);//已付款无法取消
        }
        int resultCount = orderMapper.updateStatusByUidAndOrderNo(user.getId(),orderNo);
        if(resultCount < 0){
            return ServerResponse.createByErrorMessage(Const.CANCEL_ERROR);
        }
        return ServerResponse.createBySuccessMessage(Const.CANCEL_ORDER_SUCCESS);
    }


    //整合orderItemVoList
    private OrderItemVo assembleOrderItemVo(List<OrderItem> orderItemList,Integer userId){
        OrderItemVo orderItemVo = new OrderItemVo();
        for(OrderItem oi:orderItemList){
            //单种产品的金额
            double totalPrice = (oi.getCurrentUnitPrice()).doubleValue() * oi.getQuantity();
            oi.setTotalPrice(BigDecimal.valueOf(totalPrice));
        }
        //多种产品的总额
        Double productTotalPrice = orderItemMapper.selectProductTotalPrice(userId);

        orderItemVo.setOrderItemList(orderItemList);
        orderItemVo.setImageHost("http://img.happymmall.com/");
        orderItemVo.setProductTotalPrice(productTotalPrice);
        return orderItemVo;
    }

    //整合order
    private Order assembleOrder(Order order,Integer userId){
        List<OrderItem> orderItem = orderItemMapper.selectOrderItemByUidAndOrderNo(userId,order.getOrderNo());
        order.setOrderItemVoList(orderItem);
        //支付类型
        String paymentTypeDesc = (String)Const.payType.get(order.getPaymentType());
        order.setPaymentTypeDesc( paymentTypeDesc);
        //支付状态
        String statusDesc = (String)Const.status.get(order.getStatus());
        order.setStatusDesc(statusDesc);
        return order;
    }

    //整合orderList
    private List<Order> assembleOrderList(List<Order> orderList, Integer userId,String username){
        for(Order o:orderList){
            List<OrderItem> orderItem = orderItemMapper.selectOrderItemByUidAndOrderNo(userId,o.getOrderNo());
            o.setOrderItemVoList(orderItem);
            //支付类型
            String paymentTypeDesc = (String)Const.payType.get(o.getPaymentType());
            o.setPaymentTypeDesc( paymentTypeDesc);
            //支付状态
            String statusDesc = (String)Const.status.get(o.getStatus());
            o.setStatusDesc(statusDesc);
            o.setReceiveName(username);
        }
        return orderList;
    }


    //支付
    public ServerResponse pay(HttpSession session, BigInteger orderNo, HttpServletRequest request) {
        //1、判断用户是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.NEED_LOGIN_ERROR.getDesc());//用户未登录
        }
        //2、根据orderNo查找订单信息
        Order order = orderMapper.selectOrderByOrderNo(orderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage(Const.SELECT_ORDER_ERROR);//该订单不存在
        }
        List<OrderItem> orderItemList = orderItemMapper.selectOrderItemByUidAndOrderNo(user.getId(), orderNo);
        //3、获取qrPath（二维码生成路径）
//        Object qrPath = tradePay(order,orderItemList,request);
        //4、封装在map 中
        HashMap<String, String> payMap = new HashMap<String, String>();
        payMap.put("orderNo",orderNo.toString());
        payMap.put("qrPath","");
        if(payMap == null){
            return ServerResponse.createByErrorMessage(Const.PAY_ORDER_ERROR);//支付宝生成订单失败
        }
        return ServerResponse.createSuccess(payMap);
    }

    //查询订单支付状态
    public ServerResponse queryOrderPayStatus(HttpSession session,BigInteger  orderNo) {
        return null;
    }

    //支付宝回调
    public ServerResponse alipayCallback(HttpSession session, HttpServletRequest request) {
        return null;
    }

    //支付：获取qrPath
//    private Object tradePay(Order order,List<OrderItem> orderItemList,HttpServletRequest request){
//        Log log = LogFactory.getLog(OrderServiceImpl.class);
//
//        if(order.getOrderNo() != null){
//            //引入zfbinfo.properties
//            Configs.init("zfbinfo.properties");
//            AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
//
//            //(必填)订单号
//            String  outTradeNo = order.getOrderNo().toString();
//
//            // (必填) 订单标题
//            String subject = "客都汇大润发";
//
//            //（必填）消费总金额
//            String totalAmount = order.getPayment().toString();
//
//            // (可选) 订单不可打折金额
//            String undiscountableAmount = "";
//
//            // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
//            // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
//            String sellerId = "";
//
//            // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
//            Integer num  = orderItemMapper.selectCountByUidAndOrderNo(order.getUserId(),order.getOrderNo());
//            String body = "购买商品"+num+"件"+"共"+order.getPayment()+"元";
//
//            // 商户操作员编号，添加此参数可以为商户操作员做销售统计
//            String operatorId = "test_operator_id";
//
//            // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
//            String storeId = "test_store_id";
//
//            // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
//            ExtendParams extendParams = new ExtendParams();
//            extendParams.setSysServiceProviderId("2088100200300400500");
//
//            // 支付超时，线下扫码交易定义为5分钟
//            String timeoutExpress = "5m";
//
//            // 商品明细列表，需填写购买商品详细信息，
//            List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
//            for (OrderItem item:orderItemList){
//                GoodsDetail goods = GoodsDetail.newInstance((item.getId()).toString(),item.getProductImage(),
//                        (item.getTotalPrice()).longValue(),item.getQuantity());
//                // 创建好一个商品后添加至商品明细列表
//                goodsDetailList.add(goods);
//            }
//
//
//            // 创建请求builder，设置请求参数
//            AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
//                    .setSubject(subject)
//                    .setTotalAmount(totalAmount)
//                    .setOutTradeNo(outTradeNo)
//                    .setUndiscountableAmount(undiscountableAmount)
//                    .setSellerId(sellerId)
//                    .setBody(body)
//                    .setOperatorId(operatorId)
//                    .setStoreId(storeId)
//                    .setExtendParams(extendParams)
//                    .setTimeoutExpress(timeoutExpress)
////              .setNotifyUrl("http://www.test-notify-url.com")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
//                    .setGoodsDetailList(goodsDetailList);
//
//
//            // 调用tradePay方法获取当面付应答
//
//            AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
//            switch (result.getTradeStatus()) {
//                case SUCCESS:
//                    log.info("支付宝预下单成功: )");
//
//                    AlipayTradePrecreateResponse res = result.getResponse();
//                    String basePath = request.getSession().getServletContext().getRealPath("/");
//                    String fileName = String.format("images%sqr-%s.png", File.separator, res.getOutTradeNo());
//                    String filePath = new StringBuilder(basePath).append(fileName).toString();
//
//
//                    ZxingUtils.getQRCodeImge(res.getQrCode(), 256, filePath);
//                    break;
//
//                case FAILED:
//                    log.error("支付宝预下单失败!!!");
//                    break;
//
//                case UNKNOWN:
//                    log.error("系统异常，预下单状态未知!!!");
//                    break;
//
//                default:
//                    log.error("不支持的交易状态，交易返回异常!!!");
//                    break;
//            }
//            return result.getResponse().getBody();
//        }
//        return null;
//    }
}
