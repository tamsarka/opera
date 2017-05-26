var JH;
if (!JH) JH = {};
else if (typeof JH != "object")
    throw new Error("JH already exists and is not an object");
    
if (JH.Utilities)
    throw new Error("JH.Utilities already exsists");

JH.Utilities = {
    HtmlEncode: function(textToEncode)
    {
        var result = textToEncode;

        var amp = /&/gi;
        var gt = />/gi;
        var lt = /</gi;
        var quot = /"/gi;
        var apos = /'/gi;

        var html_gt = "&gt;";
        var html_lt = "&lt;";
        var html_amp = "&amp;";
        var html_quot = "&quot;";
        var html_apos = "&apos;";

        result = result.replace(amp, html_amp);
        result = result.replace(quot, html_quot);
        result = result.replace(lt, html_lt);
        result = result.replace(gt, html_gt);
        result = result.replace(apos, html_apos);

        return result;
    },
    
    HtmlDecode: function(textToDecode)
    {
        var result = textToDecode;

        var amp = /&amp;/gi;
        var gt = /&gt;/gi;
        var lt = /&lt;/gi;
        var quot = /&quot;/gi;
        var apos = /&apos;/gi;
        var nbsp = /&nbsp;/gi;

        var html_gt = ">";
        var html_lt = "<";
        var html_amp = "&";
        var html_quot = "\"";
        var html_apos = "'";
        var html_nbsp = " ";

        result = result.replace(amp, html_amp);
        result = result.replace(quot, html_quot);
        result = result.replace(lt, html_lt);
        result = result.replace(gt, html_gt);
        result = result.replace(apos, html_apos);
        result = result.replace(nbsp, html_nbsp);

        return result;
    },
    
    HtmlEncode2: function(textToEncode)
    {
	    var result = "";
	    for (var i = 0; i<textToEncode.length; i++)
	    {
		    var currentCharCode = textToEncode.charCodeAt(i);
		    result += "&#" + currentCharCode + ";";
	    }
	    return result;
    },

    HtmlDecode2: function(textToDecode)
    {
	    var result = "";
	    
	    if (textToDecode.indexOf("&") < 0)
	    {
	        result = textToDecode;
	    }
	    else
	    {
	        for (var i=0; i<textToDecode.length; i++)
	        {
	            var charCurrent = textToDecode.charAt(i);
	            var currentEntity = "";
	            if (charCurrent == "&")
	            {
	                var endIndex = textToDecode.indexOf(";", i+1);
	                if (endIndex > 0)
	                {
	                    var entity = textToDecode.substring(i+1, endIndex);
	                    if ((entity.length > 1) && (entity.charAt(0) == "#"))
	                    {
	                        try
	                        {
	                            if ((entity.charAt(1) == "x") || (entity.charAt(1) == "X"))
	                            {
	                                currentEntity = String.fromCharCode(entity.substring(2));
	                            }
	                            else
	                            {
	                                currentEntity = String.fromCharCode(entity.substring(1));
	                            }
	                        }
	                        catch(e)
	                        {
	                            i++;
	                        }
	                    }
	                }
	            }
	            result += currentEntity;
	        }
	    }
	    
	    return this.HtmlDecode(result);
    },

    UrlEncode: function(textToEncode)
    {
	    var result = "";
	    for (var i = 0; i<textToEncode.length; i++)
	    {
		    var currentCharCode = textToEncode.charCodeAt(i).toString(16);
		    result += "%" + currentCharCode;
	    }
        return result;
    },
   
    UrlDecode: function(textToDecode)
    {
	    var result = "";
	    
	    if (textToDecode.indexOf("%") < 0)
	    {
	        result = textToDecode;
	    }
	    else
	    {
	        for (var i=0; i<textToDecode.length; i++)
	        {
	            var charCurrent = textToDecode.charAt(i);
	            var currentEntity = "";
	            if (charCurrent == "%")
	            {
	                var endIndex = textToDecode.indexOf("%", i+1);
	                if (endIndex < 0)
	                {
	                    endIndex = textToDecode.length;
	                }
	                var entity = textToDecode.substring(i+1, endIndex);
                    currentEntity = String.fromCharCode(parseInt(entity,16));
                    i += entity.length;
	            }
	            result += currentEntity;
	        }
	    }
	    return result;
    },
   
    XmlEncode: function(textToEncode)
    {
	    var result = textToEncode;

	    var amp = /&/gi;
	    var gt = />/gi;
	    var lt = /</gi;
	    var quot = /"/gi;
	    var apos = /'/gi;

	    var xml_gt = "&#62;";
	    var xml_lt = "&#38;#60;";
	    var xml_amp = "&#38;#38;";
	    var xml_quot = "&#34;";
	    var xml_apos = "&#39;";

	    result = result.replace(amp, xml_amp);
	    result = result.replace(quot, xml_quot);
	    result = result.replace(lt, xml_lt);
	    result = result.replace(gt, xml_gt);
	    result = result.replace(apos, xml_apos);
        
        return result;
    },
    
    XmlDecode: function(textToDecode)
    {
        var result = textToDecode;

        var gt = /&#62;/gi;
	    var lt = /&#38;#60;/gi;
	    var amp = /&#38;#38;/gi;   
	    var quot = /&#34;/gi;
	    var apos = /&#39;/gi;

	    var xml_gt = ">";
	    var xml_lt = "<";
	    var xml_amp = "&";
	    var xml_quot = "\"";
	    var xml_apos = "'";

	     result = result.replace(amp, xml_amp);
	    result = result.replace(quot, xml_quot);
	    result = result.replace(lt, xml_lt);
	    result = result.replace(gt, xml_gt);
	    result = result.replace(apos, xml_apos);
        
        return result;
    }
};
