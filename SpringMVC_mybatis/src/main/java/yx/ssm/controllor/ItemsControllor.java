package yx.ssm.controllor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import yx.ssm.controllor.converter.validation.ValiGroup1;
import yx.ssm.exception.CustomException;
import yx.ssm.po.ItemsCustom;
import yx.ssm.po.ItemsQueryVo;
import yx.ssm.servic.ItemsServic;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 商品的Controllor
 *
 * @RequestMapping 定义Controller方法对应的url，进行处理器映射使用 作用 1 窄化请求路径 2 限制http请求方法
 *
 * @author Administrator
 *
 */
@Controller
// 为了对url进行分类管理，可以在这里定义根路径，最终访问路径为根路径+子路径
@RequestMapping("/items")
public class ItemsControllor {

    @Autowired
    private ItemsServic itemsServic;

    // 商品分类
    //itemtypes表示最终将方法返回值放在request中的key
    @ModelAttribute("itemtypes")
    public Map<String, String> getItemTypes() {

        Map<String, String> itemTypes = new HashMap<String, String>();
        itemTypes.put("101", "数码");
        itemTypes.put("102", "母婴");

        return itemTypes;
    }


    // 商品查询
    @RequestMapping("/queryIltems")
    public ModelAndView queryIltems(ItemsQueryVo itemsqueryvo) throws Exception {

        ModelAndView modeandview = new ModelAndView();
        // 调用service查找数据库，查询商品列表
        List<ItemsCustom> itemsList = itemsServic.findItemslist(itemsqueryvo);

        modeandview.addObject("itemsList", itemsList);

        // 指定视图
        // 下边的路径，如果在试图解析器中配置jsp路径的前缀与jsp路径的后缀，修改为
        modeandview.setViewName("/WEB-INF/jsp/items/itemsList.jsp");
        // modeandview.setViewName("items/itemsList");
        return modeandview;
    }

    // 商品修改信息的展示
    // @RequestMapping("/editItems")
    // 限制http请求方法 限制为post则get无法访问
    // @RequestMapping(value="/editItems",method=
    // {RequestMethod.POST,RequestMethod.GET})
    // public ModelAndView editItems() throws Exception {
    // ModelAndView modeandview = new ModelAndView();
    //
    // // 调用service根据商品id查询商品信息
    // ItemsCustom itemsCustom = itemsServic.findItemsById(1);
    //
    // // 将商品信息放入model
    // modeandview.addObject("itemsCustom", itemsCustom);
    //
    // // 商品修改页面
    // modeandview.setViewName("/WEB-INF/jsp/items/editItems.jsp");
    // return modeandview;
    // }

    /**
     *
     * 如果controllor返回string，表示返回逻辑视图名 1 真正视图（jsp）=前缀+逻辑视图+后缀
     * 2 redirect重定向
     * 商品修改提交后，重定向到商品查询列表
     * 特点：浏览器中的url会改变。修改提交的request数据无法传到重定向地址 request无法共享
     * 3  forward页面转发 浏览器中的url不变 request可以共享
     *
     * 如果controllor返回void 同普通的servlet在形参里定义request 和response
     *
     *
     */
    @RequestMapping(value = "/editItems", method = { RequestMethod.POST, RequestMethod.GET })
    // @RequestParam里面指定request传入参数名称和形参绑定
    // 通过required指定参数是否必须传入
    // defaultValue默认值
    public String editItems(Model model,
   @RequestParam(value = "id", required = true, defaultValue = "") Integer items_id) throws Exception {

        // 调用service根据商品id查询商品信息
        ItemsCustom itemsCustom = itemsServic.findItemsById(items_id);


        //判断商品是否为空，根据id没有查询到商品，抛出异常，提示用户商品信息不存在
        if(itemsCustom == null){
            throw new CustomException("修改的商品信息不存在!");
        }

        // 通过形参里的model讲model数据传到页面
        // 相当于modelandview.addObject
        model.addAttribute("itemsCustom", itemsCustom);

        return "/WEB-INF/jsp/items/editItems.jsp";
    }

    // 商品信息修改提交
    @RequestMapping("/editItemsSubmit")
    // pojo的绑定 页面中 input的name与形参中属性名一致
    //在校验的pojo前家 @Validated  在后面加BindingResult binding接受校验出错的信息
    //校验是配对使用的 必须注意顺序  通过model把错误信息传到页面
    public String editItemsSubmit(Model model,Integer id,@ModelAttribute("items") @Validated(value = ValiGroup1.class)
            ItemsCustom itemscustom
            ,BindingResult binding,
       MultipartFile items_pic//接受图片
    ) throws Exception {

        //获取校验错误信息
        if(binding.hasErrors()) {
            List<ObjectError> errors=binding.getAllErrors();
            for(ObjectError temp:errors) {
                System.out.println(temp.getDefaultMessage());
            }
            //将错误信息传到页面
            model.addAttribute("errors", errors);
            model.addAttribute("itemsCustom",itemscustom);
            //出错重新返回到商品修改页面
            return "/WEB-INF/jsp/items/editItems.jsp";
        }
        //上传图片
        //获取上传图片的原始名称
        String orgname=items_pic.getOriginalFilename();
        if(items_pic!=null && orgname!=null && orgname.length()>0){
            //存储图片的物理路径
            String pic_path="E:\\picall\\";

            //新的图片名称
            String newfile_Picname=UUID.randomUUID()+orgname.substring(orgname.lastIndexOf("."));
            //新的图片
            File newfile=new File(pic_path+newfile_Picname);
            //将内存中的数据写入磁盘
            items_pic.transferTo(newfile);

            //将新的图片名称写入itemscustom
            itemscustom.setPic(newfile_Picname);

        }



        itemsServic.updateItems(id, itemscustom);
        return "redirect:queryIltems.action";
    }

    //查询商品信息，输出json
    //itemsView/{id}里边的{id}表示占位符，通过@PathVariable获取占位符中的参数，
    //@PathVariable中名称要和占位符一致，形参名无需和其一致
    //如果占位符中的名称和形参名一致，在@PathVariable可以不指定名称
    @RequestMapping("itemsView/{id}")
    public @ResponseBody ItemsCustom itemsView(@PathVariable("id") Integer id) throws Exception{
        ItemsCustom itemscustitems=itemsServic.findItemsById(id);


        return itemscustitems;
    }


    // 批量删除商品信息
    @RequestMapping("/deleteItems")
    public String deleteItems(Integer[] items_id) throws Exception {

        //调用service删除商品

        return "/WEB-INF/jsp/success.jsp";
    }

    //进入批量商品修改页面
    @RequestMapping("/editItemsQuery")
    public ModelAndView editItemsQuery(ItemsQueryVo itemsqueryvo) throws Exception {

        ModelAndView modeandview = new ModelAndView();
        // 调用service查找数据库，查询商品列表
        List<ItemsCustom> itemsList = itemsServic.findItemslist(itemsqueryvo);

        modeandview.addObject("itemsList", itemsList);

        // 指定视图
        // 下边的路径，如果在试图解析器中配置jsp路径的前缀与jsp路径的后缀，修改为
        modeandview.setViewName("/WEB-INF/jsp/items/editItemsQuery.jsp");
        // modeandview.setViewName("items/itemsList");
        return modeandview;
    }



    //批量商品修改的提交
    //通过ItemsQueryVo接受批量修改的商品信息，存储到itemsqueryvo中itemsList
    @RequestMapping("/editAllItemsSubmit")
    public String editAllItemsSubmit(ItemsQueryVo itemsqueryvo) throws Exception  {


        return "/WEB-INF/jsp/success.jsp";
    }

}



