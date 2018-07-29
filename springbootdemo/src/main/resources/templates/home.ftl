<html>
<#-- 注释部分 -->
welcome to home ! ${value1} <br>
<#list colors as color>
    I like ${color} ! <br>
</#list> <br>
<#list maps?keys as key>
    <tr>
        <td>key : ${key}</td>
        <td>value : ${maps[key]}</td>
    </tr>
    <br>
</#list> <br>
user : ${user.name} <br>
description1 : ${user.description} <br>
description2 : ${user.getDescription()} <br>
<#assign title = "nowcoder_title">
title : ${title} <br>

<#macro render_color index color>
    Color Render Macro ${index} ${color}
</#macro>
<@render_color index = "1" color = "green"/> <br>
<#list colors as p>
    <@render_color index = p_index color = p/> <br>
</#list>
</html>