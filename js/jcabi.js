$(document).ready(function(){$(".menu-button").click(function(){"menu-off"===$("#here-goes-menu").attr("class")?($("#menu").clone().attr("class","mobile-menu").appendTo("#here-goes-menu"),$("#here-goes-menu").attr("class","menu-on")):($(".mobile-menu").hide(),$("#here-goes-menu").attr("class","menu-off"))});$("a.ico").click(function(a){a.preventDefault();a=$(this);window.open(a.attr("href"),a.attr("title"),"width\x3d640,height\x3d300")});$("a.ico").each(function(){this.href=this.href.replace("URL",
encodeURIComponent(window.location));this.href=this.href.replace("TITLE",encodeURIComponent($(document).find("title").text()))})});