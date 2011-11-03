(function() {
    var g = true,h = null,j = false,aa = (new Date).getTime(),k = function(a) {
        var b = (new Date).getTime() - aa;
        b = "&dtd=" + (b < 1E3 ? b : "M");
        return a + b
    };
    var l = this,ba = function(a, b, c) {
        a = a.split(".");
        c = c || l;
        !(a[0]in c) && c.execScript && c.execScript("var " + a[0]);
        for (var d; a.length && (d = a.shift());)if (!a.length && b !== undefined)c[d] = b; else c = c[d] ? c[d] : (c[d] = {})
    },n = function(a) {
        var b = typeof a;
        if (b == "object")if (a) {
            if (a instanceof Array || !(a instanceof Object) && Object.prototype.toString.call(a) == "[object Array]" || typeof a.length == "number" && typeof a.splice != "undefined" && typeof a.propertyIsEnumerable != "undefined" && !a.propertyIsEnumerable("splice"))return"array";
            if (!(a instanceof Object) && (Object.prototype.toString.call(a) == "[object Function]" || typeof a.call != "undefined" && typeof a.propertyIsEnumerable != "undefined" && !a.propertyIsEnumerable("call")))return"function"
        } else return"null"; else if (b == "function" && typeof a.call == "undefined")return"object";
        return b
    },o = function(a) {
        return n(a) == "array"
    },ca = function(a) {
        var b = n(a);
        return b == "array" || b == "object" && typeof a.length == "number"
    },p = function(a) {
        return typeof a == "string"
    },da = function(a) {
        a = n(a);
        return a == "object" ||
                a == "array" || a == "function"
    },ea = function(a) {
        var b = n(a);
        if (b == "object" || b == "array") {
            if (a.clone)return a.clone.call(a);
            b = b == "array" ? [] : {};
            for (var c in a)b[c] = ea(a[c]);
            return b
        }
        return a
    },fa = function(a, b) {
        var c = b || l;
        if (arguments.length > 2) {
            var d = Array.prototype.slice.call(arguments, 2);
            return function() {
                var e = Array.prototype.slice.call(arguments);
                Array.prototype.unshift.apply(e, d);
                return a.apply(c, e)
            }
        } else return function() {
            return a.apply(c, arguments)
        }
    },ga = Date.now || function() {
        return+new Date
    },q = function(a, b, c) {
        ba(a, b, c)
    };
    var r = Array.prototype,ha = r.forEach ? function(a, b, c) {
        r.forEach.call(a, b, c)
    } : function(a, b, c) {
        for (var d = a.length,e = p(a) ? a.split("") : a,f = 0; f < d; f++)f in e && b.call(c, e[f], f, a)
    },ia = function() {
        return r.concat.apply(r, arguments)
    },ja = function(a) {
        if (o(a))return ia(a); else {
            for (var b = [],c = 0,d = a.length; c < d; c++)b[c] = a[c];
            return b
        }
    };
    var s = function(a, b) {
        this.width = a;
        this.height = b
    };
    s.prototype.clone = function() {
        return new s(this.width, this.height)
    };
    s.prototype.toString = function() {
        return"(" + this.width + " x " + this.height + ")"
    };
    s.prototype.ceil = function() {
        this.width = Math.ceil(this.width);
        this.height = Math.ceil(this.height);
        return this
    };
    s.prototype.floor = function() {
        this.width = Math.floor(this.width);
        this.height = Math.floor(this.height);
        return this
    };
    s.prototype.round = function() {
        this.width = Math.round(this.width);
        this.height = Math.round(this.height);
        return this
    };
    s.prototype.scale = function(a) {
        this.width *= a;
        this.height *= a;
        return this
    };
    var ka = function(a, b, c) {
        for (var d in a)b.call(c, a[d], d, a)
    };
    var qa = function(a, b) {
        if (b)return a.replace(la, "&amp;").replace(ma, "&lt;").replace(na, "&gt;").replace(oa, "&quot;"); else {
            if (!pa.test(a))return a;
            if (a.indexOf("&") != -1)a = a.replace(la, "&amp;");
            if (a.indexOf("<") != -1)a = a.replace(ma, "&lt;");
            if (a.indexOf(">") != -1)a = a.replace(na, "&gt;");
            if (a.indexOf('"') != -1)a = a.replace(oa, "&quot;");
            return a
        }
    },la = /&/g,ma = /</g,na = />/g,oa = /\"/g,pa = /[&<>\"]/,ta = function(a) {
        if (a.indexOf("&") != -1)return"document"in l && a.indexOf("<") == -1 ? ra(a) : sa(a);
        return a
    },ra = function(a) {
        var b =
                l.document.createElement("a");
        b.innerHTML = a;
        b.normalize && b.normalize();
        a = b.firstChild.nodeValue;
        b.innerHTML = "";
        return a
    },sa = function(a) {
        return a.replace(/&([^;]+);/g, function(b, c) {
            switch (c) {
                case "amp":
                    return"&";
                case "lt":
                    return"<";
                case "gt":
                    return">";
                case "quot":
                    return'"';
                default:
                    if (c.charAt(0) == "#") {
                        c = Number("0" + c.substr(1));
                        if (!isNaN(c))return String.fromCharCode(c)
                    }
                    return b
            }
        })
    },ua = function(a, b) {
        for (var c = b.length,d = 0; d < c; d++) {
            var e = c == 1 ? b : b.charAt(d);
            if (a.charAt(0) == e && a.charAt(a.length - 1) == e)return a.substring(1,
                    a.length - 1)
        }
        return a
    },va = function(a, b) {
        var c = 0;
        a = String(a).replace(/^[\s\xa0]+|[\s\xa0]+$/g, "").split(".");
        b = String(b).replace(/^[\s\xa0]+|[\s\xa0]+$/g, "").split(".");
        for (var d = Math.max(a.length, b.length),e = 0; c == 0 && e < d; e++) {
            var f = a[e] || "",i = b[e] || "",m = new RegExp("(\\d*)(\\D*)", "g"),z = new RegExp("(\\d*)(\\D*)", "g");
            do{
                var G = m.exec(f) || ["","",""],H = z.exec(i) || ["","",""];
                if (G[0].length == 0 && H[0].length == 0)break;
                c = G[1].length == 0 ? 0 : parseInt(G[1], 10);
                var mb = H[1].length == 0 ? 0 : parseInt(H[1], 10);
                c = t(c, mb) ||
                        t(G[2].length == 0, H[2].length == 0) || t(G[2], H[2])
            } while (c == 0)
        }
        return c
    },t = function(a, b) {
        if (a < b)return-1; else if (a > b)return 1;
        return 0
    };
    ga();
    var u,v,w,x,y,wa,xa,ya,za,Aa = function() {
        return l.navigator ? l.navigator.userAgent : h
    },A = function() {
        return l.navigator
    },Ba = function() {
        y = x = w = v = u = j;
        var a;
        if (a = Aa()) {
            var b = A();
            u = a.indexOf("Opera") == 0;
            v = !u && a.indexOf("MSIE") != -1;
            x = (w = !u && a.indexOf("WebKit") != -1) && a.indexOf("Mobile") != -1;
            y = !u && !w && b.product == "Gecko"
        }
    };
    Ba();
    var B = u,C = v,Ca = y,D = w,Da = x,Ea = function() {
        var a = A();
        return a && a.platform || ""
    },E = Ea(),Fa = function() {
        wa = E.indexOf("Mac") != -1;
        xa = E.indexOf("Win") != -1;
        ya = E.indexOf("Linux") != -1;
        za = !!A() && (A().appVersion || "").indexOf("X11") != -1
    };
    Fa();
    var Ga = wa,Ha = xa,Ia = ya,Ja = function() {
        var a = "",b;
        if (B && l.opera) {
            a = l.opera.version;
            a = typeof a == "function" ? a() : a
        } else {
            if (Ca)b = /rv\:([^\);]+)(\)|;)/; else if (C)b = /MSIE\s+([^\);]+)(\)|;)/; else if (D)b = /WebKit\/(\S+)/;
            if (b)a = (a = b.exec(Aa())) ? a[1] : ""
        }
        return a
    },Ka = Ja(),La = {},F = function(a) {
        return La[a] || (La[a] = va(Ka, a) >= 0)
    };
    var Ma = function(a) {
        return p(a) ? document.getElementById(a) : a
    },Na = Ma,Pa = function(a, b) {
        ka(b, function(c, d) {
            if (d == "style")a.style.cssText = c; else if (d == "class")a.className = c; else if (d == "for")a.htmlFor = c; else if (d in Oa)a.setAttribute(Oa[d], c); else a[d] = c
        })
    },Oa = {cellpadding:"cellPadding",cellspacing:"cellSpacing",colspan:"colSpan",rowspan:"rowSpan",valign:"vAlign",height:"height",width:"width",usemap:"useMap",frameborder:"frameBorder",type:"type"},Qa = function(a) {
        var b = a.document;
        if (D && !F("500") && !Da) {
            if (typeof a.innerHeight ==
                    "undefined")a = window;
            b = a.innerHeight;
            var c = a.document.documentElement.scrollHeight;
            if (a == a.top)if (c < b)b -= 15;
            return new s(a.innerWidth, b)
        }
        a = b.compatMode == "CSS1Compat" && (!B || B && F("9.50")) ? b.documentElement : b.body;
        return new s(a.clientWidth, a.clientHeight)
    },Sa = function() {
        return Ra(document, arguments)
    },Ra = function(a, b) {
        var c = b[0],d = b[1];
        if (C && d && (d.name || d.type)) {
            c = ["<",c];
            d.name && c.push(' name="', qa(d.name), '"');
            if (d.type) {
                c.push(' type="', qa(d.type), '"');
                d = ea(d);
                delete d.type
            }
            c.push(">");
            c = c.join("")
        }
        var e =
                a.createElement(c);
        if (d)if (p(d))e.className = d; else Pa(e, d);
        if (b.length > 2) {
            d = function(i) {
                if (i)e.appendChild(p(i) ? a.createTextNode(i) : i)
            };
            for (c = 2; c < b.length; c++) {
                var f = b[c];
                ca(f) && !(da(f) && f.nodeType > 0) ? ha(Ta(f) ? ja(f) : f, d) : d(f)
            }
        }
        return e
    },Ua = function(a, b) {
        a.appendChild(b)
    },Ta = function(a) {
        if (a && typeof a.length == "number")if (da(a))return typeof a.item == "function" || typeof a.item == "string"; else if (n(a) == "function")return typeof a.item == "function";
        return j
    };

    function I(a, b) {
        a = parseFloat(a);
        return isNaN(a) || a > 1 || a < 0 ? b : a
    }

    function Va(a, b) {
        if (a == "true")return g;
        if (a == "false")return j;
        return b
    }

    function J(a, b) {
        var c = /^([\w-]+\.)+[\w-]{2,}(\:[0-9]+)?$/;
        return c.test(a) ? a : b
    }

    ;
    var Wa = document,Xa = Va("false", j),Ya = Va("false", j),Za = Va("false", j),K = window;
    var $a = "pagead2.googlesyndication.com",ab = "googleads.g.doubleclick.net",bb = "googleads2.g.doubleclick.net",cb = "pubads.g.doubleclick.net",db = "securepubads.g.doubleclick.net",eb = "partner.googleadservices.com",fb = J("pagead2.googlesyndication.com", $a),gb = J("googleads.g.doubleclick.net", ab),hb = J("", bb),ib = J("pagead2.googlesyndication.com", $a);
    J("pubads.g.doubleclick.net", cb);
    J("partner.googleadservices.com", eb);
    J("securepubads.g.doubleclick.net", db);
    var L = function(a, b) {
        for (var c in a)Object.prototype.hasOwnProperty.call(a, c) && b.call(h, a[c], c, a)
    },jb = function(a) {
        if (arguments.length < 2)return a.length;
        for (var b = 1,c = arguments.length; b < c; ++b)a.push(arguments[b]);
        return a.length
    };

    function M(a) {
        return typeof encodeURIComponent == "function" ? encodeURIComponent(a) : escape(a)
    }

    function kb(a, b, c) {
        var d = document.createElement("script");
        d.type = "text/javascript";
        if (b)d.onload = b;
        if (c)d.id = c;
        d.src = a;
        var e = document.getElementsByTagName("head")[0];
        if (!e)return j;
        window.setTimeout(function() {
            e.appendChild(d)
        }, 0);
        return g
    }

    function lb(a, b) {
        a.google_image_requests || (a.google_image_requests = []);
        var c = new Image;
        c.src = b;
        a.google_image_requests.push(c)
    }

    function nb(a) {
        if (a in ob)return ob[a];
        return ob[a] = navigator.userAgent.toLowerCase().indexOf(a) != -1
    }

    var ob = {};

    function pb() {
        if (navigator.plugins && navigator.mimeTypes.length) {
            var a = navigator.plugins["Shockwave Flash"];
            if (a && a.description)return a.description.replace(/([a-zA-Z]|\s)+/, "").replace(/(\s)+r/, ".")
        } else if (navigator.userAgent && navigator.userAgent.indexOf("Windows CE") >= 0) {
            a = 3;
            for (var b = 1; b;)try {
                b = new ActiveXObject("ShockwaveFlash.ShockwaveFlash." + (a + 1));
                a++
            } catch(c) {
                b = h
            }
            return a.toString()
        } else if (nb("msie") && !window.opera) {
            b = h;
            try {
                b = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7")
            } catch(d) {
                a =
                        0;
                try {
                    b = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");
                    a = 6;
                    b.AllowScriptAccess = "always"
                } catch(e) {
                    if (a == 6)return a.toString()
                }
                try {
                    b = new ActiveXObject("ShockwaveFlash.ShockwaveFlash")
                } catch(f) {
                }
            }
            if (b) {
                a = b.GetVariable("$version").split(" ")[1];
                return a.replace(/,/g, ".")
            }
        }
        return"0"
    }

    function N(a) {
        var b = a.google_ad_format;
        if (b)return b.indexOf("_0ads") > 0;
        return a.google_ad_output != "html" && a.google_num_radlinks > 0
    }

    function O(a) {
        return!!a && a.indexOf("_sdo") != -1
    }

    function P(a, b) {
        var c = Math.random();
        if (c < b) {
            b = Math.floor(c / b * a.length);
            return a[b]
        }
        return""
    }

    var qb = function(a) {
        a.u_tz = -(new Date).getTimezoneOffset();
        a.u_his = window.history.length;
        a.u_java = navigator.javaEnabled();
        if (window.screen) {
            a.u_h = window.screen.height;
            a.u_w = window.screen.width;
            a.u_ah = window.screen.availHeight;
            a.u_aw = window.screen.availWidth;
            a.u_cd = window.screen.colorDepth
        }
        if (navigator.plugins)a.u_nplug = navigator.plugins.length;
        if (navigator.mimeTypes)a.u_nmime = navigator.mimeTypes.length
    },rb = function(a) {
        var b = K;
        if (a && b.top != b)b = b.top;
        try {
            return b.document && !b.document.body ? new s(-1,
                    -1) : Qa(b || window)
        } catch(c) {
            return new s(-12245933, -12245933)
        }
    },sb = function(a, b) {
        var c = a.length;
        if (c == 0)return 0;
        b = b || 305419896;
        for (var d = 0; d < c; d++) {
            var e = a.charCodeAt(d);
            b ^= (b << 5) + (b >> 2) + e & 4294967295
        }
        return b
    },tb = function(a) {
        if (a == a.top)return 0;
        var b = [];
        b.push(document.URL);
        a.name && b.push(a.name);
        a = g;
        a = rb(!a);
        b.push(a.width.toString());
        b.push(a.height.toString());
        b = sb(b.join(""));
        return b > 0 ? b : 4294967296 + b
    };
    var ub = {google_ad_channel:"channel",google_ad_host:"host",google_ad_host_channel:"h_ch",google_ad_host_tier_id:"ht_id",google_ad_section:"region",google_ad_type:"ad_type",google_adtest:"adtest",google_allow_expandable_ads:"ea",google_alternate_ad_url:"alternate_ad_url",google_alternate_color:"alt_color",google_bid:"bid",google_city:"gcs",google_color_bg:"color_bg",google_color_border:"color_border",google_color_line:"color_line",google_color_link:"color_link",google_color_text:"color_text",google_color_url:"color_url",
        google_contents:"contents",google_country:"gl",google_cust_age:"cust_age",google_cust_ch:"cust_ch",google_cust_gender:"cust_gender",google_cust_id:"cust_id",google_cust_interests:"cust_interests",google_cust_job:"cust_job",google_cust_l:"cust_l",google_cust_lh:"cust_lh",google_cust_u_url:"cust_u_url",google_disable_video_autoplay:"disable_video_autoplay",google_ed:"ed",google_encoding:"oe",google_feedback:"feedback_link",google_flash_version:"flash",google_font_face:"f",google_font_size:"fs",google_hints:"hints",
        google_kw:"kw",google_kw_type:"kw_type",google_language:"hl",google_page_url:"url",google_region:"gr",google_reuse_colors:"reuse_colors",google_safe:"adsafe",google_tag_info:"gut",google_targeting:"targeting",google_ui_features:"ui",google_ui_version:"uiv",google_video_doc_id:"video_doc_id",google_video_product_type:"video_product_type"},vb = {google_ad_client:"client",google_ad_format:"format",google_ad_output:"output",google_ad_callback:"callback",google_ad_height:"h",google_ad_override:"google_ad_override",
        google_ad_slot:"slotname",google_ad_width:"w",google_ctr_threshold:"ctr_t",google_image_size:"image_size",google_last_modified_time:"lmt",google_max_num_ads:"num_ads",google_max_radlink_len:"max_radlink_len",google_num_radlinks:"num_radlinks",google_num_radlinks_per_unit:"num_radlinks_per_unit",google_only_ads_with_video:"only_ads_with_video",google_rl_dest_url:"rl_dest_url",google_rl_filtering:"rl_filtering",google_rl_mode:"rl_mode",google_rt:"rt",google_skip:"skip"},wb = {google_only_pyv_ads:"pyv",
        google_with_pyv_ads:"withpyv"};

    function xb(a, b) {
        try {
            return a.top.document.URL == b.URL
        } catch(c) {
        }
        return j
    }

    function yb(a, b, c, d) {
        c = c || a.google_ad_width;
        d = d || a.google_ad_height;
        if (xb(a, b))return j;
        var e = b.documentElement;
        if (c && d) {
            var f = 1,i = 1;
            if (a.innerHeight) {
                f = a.innerWidth;
                i = a.innerHeight
            } else if (e && e.clientHeight) {
                f = e.clientWidth;
                i = e.clientHeight
            } else if (b.body) {
                f = b.body.clientWidth;
                i = b.body.clientHeight
            }
            if (i > 2 * d || f > 2 * c)return j
        }
        return g
    }

    function zb(a, b) {
        L(b, function(c, d) {
            a["google_" + d] = c
        })
    }

    function Ab(a, b) {
        if (!b)return a.URL;
        return a.referrer
    }

    function Bb(a, b) {
        if (!b && a.google_referrer_url == h)return"0"; else if (b && a.google_referrer_url == h)return"1"; else if (!b && a.google_referrer_url != h)return"2"; else if (b && a.google_referrer_url != h)return"3";
        return"4"
    }

    function Cb(a, b, c, d) {
        a.page_url = Ab(c, d);
        a.page_location = h
    }

    function Db(a, b, c, d) {
        a.page_url = b.google_page_url;
        a.page_location = Ab(c, d) || "EMPTY"
    }

    function Eb(a, b) {
        var c = {},d = yb(a, b, a.google_ad_width, a.google_ad_height);
        c.iframing = Bb(a, d);
        a.google_page_url ? Db(c, a, b, d) : Cb(c, a, b, d);
        c.last_modified_time = b.URL == c.page_url ? Date.parse(b.lastModified) / 1E3 : h;
        c.referrer_url = d ? a.google_referrer_url : a.google_page_url && a.google_referrer_url ? a.google_referrer_url : b.referrer;
        return c
    }

    function Fb(a) {
        var b = {},c = a.URL.substring(a.URL.lastIndexOf("http"));
        b.iframing = h;
        b.page_url = c;
        b.page_location = a.URL;
        b.last_modified_time = h;
        b.referrer_url = c;
        return b
    }

    function Gb(a, b) {
        b = Hb(a, b);
        zb(a, b)
    }

    function Hb(a, b) {
        return a = a.google_page_url == h && Ib[b.domain] ? Fb(b) : Eb(a, b)
    }

    var Ib = {};
    Ib["ad.yieldmanager.com"] = g;
    var Jb = function(a, b, c) {
        b = fa(b, l, a);
        a = window.onerror;
        window.onerror = b;
        try {
            c()
        } catch(d) {
            c = d.toString();
            var e = "";
            if (d.fileName)e = d.fileName;
            var f = -1;
            if (d.lineNumber)f = d.lineNumber;
            b = b(c, e, f);
            if (!b)throw d;
        }
        window.onerror = a
    };
    q("google_protectAndRun", Jb);
    var Lb = function(a, b, c, d) {
        if (Math.random() < 0.01) {
            var e = Wa;
            a = ["http://",ib,"/pagead/gen_204","?id=jserror","&jscb=",Xa ? 1 : 0,"&jscd=",Ya ? 1 : 0,"&context=",M(a),"&msg=",M(b),"&file=",M(c),"&line=",M(d.toString()),"&url=",M(e.URL.substring(0, 512)),"&ref=",M(e.referrer.substring(0, 512))];
            a.push(Kb());
            lb(K, a.join(""))
        }
        return!Za
    };
    q("google_handleError", Lb);
    var Nb = function(a) {
        Mb |= a
    },Mb = 0,Kb = function() {
        var a = ["&client=",M(K.google_ad_client),"&format=",M(K.google_ad_format),"&slotname=",M(K.google_ad_slot),"&output=",M(K.google_ad_output),"&ad_type=",M(K.google_ad_type)];
        return a.join("")
    };
    var Q = "",Qb = function() {
        if (window.google_ad_frameborder == h)window.google_ad_frameborder = 0;
        if (window.google_ad_output == h)window.google_ad_output = "html";
        if (O(window.google_ad_format)) {
            var a = window.google_ad_format.match(/^(\d+)x(\d+)_.*/);
            if (a) {
                window.google_ad_width = parseInt(a[1], 10);
                window.google_ad_height = parseInt(a[2], 10);
                window.google_ad_output = "html"
            }
        }
        window.google_ad_format = Ob(window.google_ad_format, window.google_ad_output, window.google_ad_width, window.google_ad_height, window.google_ad_slot,
                !!window.google_override_format);
        Q = window.google_ad_client || "";
        window.google_ad_client = Pb(window.google_ad_format, window.google_ad_client);
        Gb(window, document);
        if (window.google_num_slots_by_channel == h)window.google_num_slots_by_channel = {};
        if (window.google_viewed_host_channels == h)window.google_viewed_host_channels = {};
        if (window.google_num_slots_by_client == h)window.google_num_slots_by_client = {};
        if (window.google_prev_ad_formats_by_region == h)window.google_prev_ad_formats_by_region = {};
        if (window.google_prev_ad_slotnames_by_region ==
                h)window.google_prev_ad_slotnames_by_region = {};
        if (window.google_correlator == h)window.google_correlator = (new Date).getTime();
        if (window.google_adslot_loaded == h)window.google_adslot_loaded = {};
        if (window.google_adContentsBySlot == h)window.google_adContentsBySlot = {};
        if (window.google_flash_version == h)window.google_flash_version = pb();
        if (window.google_new_domain_checked == h)window.google_new_domain_checked = 0;
        if (window.google_new_domain_enabled == h)window.google_new_domain_enabled = 0;
        if (!window.google_num_ad_slots)window.google_num_ad_slots =
                0;
        if (!window.google_num_0ad_slots)window.google_num_0ad_slots = 0;
        if (!window.google_num_sdo_slots)window.google_num_sdo_slots = 0;
        window.google_ad_section = window.google_ad_section || window.google_ad_region || "";
        window.google_country = window.google_country || window.google_gl || "";
        a = (new Date).getTime();
        if (o(window.google_color_bg))window.google_color_bg = R(window.google_color_bg, a);
        if (o(window.google_color_text))window.google_color_text = R(window.google_color_text, a);
        if (o(window.google_color_link))window.google_color_link =
                R(window.google_color_link, a);
        if (o(window.google_color_url))window.google_color_url = R(window.google_color_url, a);
        if (o(window.google_color_border))window.google_color_border = R(window.google_color_border, a);
        if (o(window.google_color_line))window.google_color_line = R(window.google_color_line, a)
    },Rb = function(a) {
        L(ub, function(b, c) {
            a[c] = h
        });
        L(vb, function(b, c) {
            a[c] = h
        });
        L(wb, function(b, c) {
            a[c] = h
        });
        a.google_container_id = h;
        a.google_eids = h;
        a.google_page_location = h;
        a.google_referrer_url = h;
        a.google_ad_region = h;
        a.google_gl = h
    },R = function(a, b) {
        Nb(2);
        return a[b % a.length]
    },Pb = function(a, b) {
        if (!b)return"";
        b = b.toLowerCase();
        return b = O(a) ? Sb(b) : Tb(b)
    },Tb = function(a) {
        if (a && a.substring(0, 3) != "ca-")a = "ca-" + a;
        return a
    },Sb = function(a) {
        if (a && a.substring(0, 7) != "ca-aff-")a = "ca-aff-" + a;
        return a
    },Ob = function(a, b, c, d, e, f) {
        if (!a && b == "html")a = c + "x" + d;
        return a = Ub(a, e, f) ? a.toLowerCase() : ""
    },Ub = function(a, b, c) {
        if (!a)return j;
        if (!b)return g;
        return c
    };
    var S = document,T = navigator,U = window;

    function Vb() {
        var a = S.cookie,b = Math.round((new Date).getTime() / 1E3),c = U.google_analytics_domain_name;
        c = typeof c == "undefined" ? Wb("auto") : Wb(c);
        var d = a.indexOf("__utma=" + c + ".") > -1,e = a.indexOf("__utmb=" + c) > -1,f = a.indexOf("__utmc=" + c) > -1,i = {},m = !!U && !!U.gaGlobal;
        if (d) {
            a = a.split("__utma=" + c + ".")[1].split(";")[0].split(".");
            i.sid = e && f ? a[3] + "" : m && U.gaGlobal.sid ? U.gaGlobal.sid : b + "";
            i.vid = a[0] + "." + a[1];
            i.from_cookie = g
        } else {
            i.sid = m && U.gaGlobal.sid ? U.gaGlobal.sid : b + "";
            i.vid = m && U.gaGlobal.vid ? U.gaGlobal.vid :
                    (Math.round(Math.random() * 2147483647) ^ Xb() & 2147483647) + "." + b;
            i.from_cookie = j
        }
        i.dh = c;
        i.hid = m && U.gaGlobal.hid ? U.gaGlobal.hid : Math.round(Math.random() * 2147483647);
        return U.gaGlobal = i
    }

    function Xb() {
        var a = S.cookie ? S.cookie : "",b = U.history.length,c,d = [T.appName,T.version,T.language ? T.language : T.browserLanguage,T.platform,T.userAgent,T.javaEnabled() ? 1 : 0].join("");
        if (U.screen)d += U.screen.width + "x" + U.screen.height + U.screen.colorDepth; else if (U.java) {
            c = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            d += c.screen.width + "x" + c.screen.height
        }
        d += a;
        d += S.referrer ? S.referrer : "";
        for (a = d.length; b > 0;)d += b-- ^ a++;
        return Yb(d)
    }

    function Yb(a) {
        var b = 1,c = 0,d;
        if (!(a == undefined || a == "")) {
            b = 0;
            for (d = a.length - 1; d >= 0; d--) {
                c = a.charCodeAt(d);
                b = (b << 6 & 268435455) + c + (c << 14);
                c = b & 266338304;
                b = c != 0 ? b ^ c >> 21 : b
            }
        }
        return b
    }

    function Wb(a) {
        if (!a || a == "" || a == "none")return 1;
        if ("auto" == a) {
            a = S.domain;
            if ("www." == a.substring(0, 4))a = a.substring(4, a.length)
        }
        return Yb(a.toLowerCase())
    }

    ;
    var V = function() {
        this.defaultBucket = [];
        this.layers = {};
        for (var a = 0,b = arguments.length; a < b; ++a)this.layers[arguments[a]] = ""
    },Zb = function(a) {
        var b = new V;
        if (a) {
            if (a.defaultBucket && o(a.defaultBucket))for (var c = 0,d = a.defaultBucket.length; c < d; ++c)b.defaultBucket.push(a.defaultBucket[c]);
            L(a.layers, fa(V.prototype.f, b))
        }
        return b
    };
    V.prototype.f = function(a, b) {
        this.layers[b] = a
    };
    V.prototype.z = function(a, b) {
        if (a == "")return"";
        if (!b) {
            this.defaultBucket.push(a);
            return a
        }
        if (this.layers.hasOwnProperty(b))return this.layers[b] = a;
        return""
    };
    V.prototype.d = function(a, b, c) {
        if (!c || this.p(c)) {
            var d = Math.random();
            if (d < b) {
                b = Math.floor(a.length * d / b);
                if (a = a[b])return this.z(a, c)
            }
        }
        return""
    };
    V.prototype.p = function(a) {
        return this.layers.hasOwnProperty(a) && this.layers[a] == ""
    };
    V.prototype.a = function(a) {
        if (this.layers.hasOwnProperty(a))return this.layers[a];
        return""
    };
    V.prototype.o = function() {
        var a = [],b = function(c) {
            c != "" && a.push(c)
        };
        L(this.layers, b);
        if (this.defaultBucket.length > 0 && a.length > 0)return this.defaultBucket.join(",") + "," + a.join(",");
        return this.defaultBucket.join(",") + a.join(",")
    };
    var $b = {google:1,googlegroups:1,gmail:1,googlemail:1,googleimages:1,googleprint:1};

    function ac(a) {
        a = a.google_page_location || a.google_page_url;
        if (!a)return j;
        a = a.toString();
        if (a.indexOf("http://") == 0)a = a.substring(7, a.length); else if (a.indexOf("https://") == 0)a = a.substring(8, a.length);
        var b = a.indexOf("/");
        if (b == -1)b = a.length;
        a = a.substring(0, b);
        a = a.split(".");
        b = j;
        if (a.length >= 3)b = a[a.length - 3]in $b;
        if (a.length >= 2)b = b || a[a.length - 2]in $b;
        return b
    }

    function bc(a, b, c) {
        if (ac(a)) {
            a.google_new_domain_checked = 1;
            return j
        }
        if (a.google_new_domain_checked == 0) {
            var d = Math.random();
            if (d <= c) {
                c = "http://" + gb + "/pagead/test_domain.js";
                d = "script";
                b.write("<" + d + ' src="' + c + '"></' + d + ">");
                a.google_new_domain_checked = 1;
                return g
            }
        }
        return j
    }

    var cc = function(a, b) {
        if (!b)return j;
        if (b.a("1") == "44901211")return 0 == a % 2;
        if (b.a("1") == "44901215")return 0 == Math.floor(a / 2) % 2;
        if (b.a("1") == "44901216")return 1 == Math.floor(a / 2) % 2;
        return j
    };

    function dc(a, b, c) {
        if (!ac(a) && a.google_new_domain_enabled == 1)return cc(b, c) ? "http://" + hb : "http://" + gb;
        return"http://" + ib
    }

    ;
    var W = function(a) {
        this.A = a;
        this.i = [];
        this.h = 0;
        this.b = [];
        this.t = 0;
        this.c = [];
        this.r = j;
        this.j = this.k = "";
        this.q = j
    };
    W.prototype.v = function(a, b) {
        var c = this.A[b],d = this.i;
        this.A[b] = function(e) {
            if (e && e.length > 0) {
                var f = e.length > 1 ? e[1].url : h;
                d.push([a,ta(e[0].url),f])
            }
            c(e)
        }
    };
    W.prototype.u = function() {
        this.h++
    };
    W.prototype.w = function(a) {
        this.b.push(a)
    };
    W.prototype.s = function() {
        if (!this.r) {
            kb("http://" + fb + "/pagead/osd.js");
            this.r = g
        }
    };
    W.prototype.l = function(a) {
        if (this.h > 0)for (var b = document.getElementsByTagName("iframe"),c = this.q ? "google_ads_iframe_" : "google_ads_frame",d = 0; d < b.length; d++) {
            var e = b.item(d);
            e.src && e.name && e.name.indexOf(c) == 0 && a(e, e.src)
        }
    };
    W.prototype.m = function(a) {
        var b = this.i;
        if (b.length > 0)for (var c = document.getElementsByTagName("a"),d = 0; d < c.length; d++)for (var e = 0; e < b.length; e++)if (c.item(d).href == b[e][1]) {
            var f = c.item(d).parentNode;
            if (b[e][2])for (var i = f,m = 0; m < 4; m++) {
                if (i.innerHTML.indexOf(b[e][2]) > 0) {
                    f = i;
                    break
                }
                i = i.parentNode
            }
            a(f, b[e][0]);
            b.splice(e, 1);
            break
        }
    };
    W.prototype.n = function(a) {
        for (var b = 0; b < this.b.length; b++) {
            var c = this.b[b],d = ec(c);
            if (d)(d = document.getElementById("google_ads_div_" + d)) && a(d, c)
        }
    };
    W.prototype.e = function(a) {
        this.m(a);
        this.n(a);
        this.l(a)
    };
    W.prototype.setupOsd = function(a, b, c) {
        this.t = a;
        this.k = b;
        this.j = c
    };
    W.prototype.getOsdMode = function() {
        return this.t
    };
    W.prototype.getEid = function() {
        return this.k
    };
    W.prototype.getCorrelator = function() {
        return this.j
    };
    W.prototype.g = function() {
        return this.i.length + this.h + this.b.length
    };
    W.prototype.setValidAdBlockTypes = function(a) {
        this.c = a
    };
    W.prototype.registerAdBlockByType = function(a, b, c) {
        if (this.c.length > 0) {
            for (var d = 0; d < this.c.length; d++)if (this.c[d] == a) {
                this.q = c;
                if (a == "js")this.v(b, "google_ad_request_done"); else if (a == "html")this.u(); else a == "json_html" && this.w(b)
            }
            this.s()
        }
    };
    var ec = function(a) {
        if ((a = a.match(/[&\?](?:slotname)=([^&]+)/)) && a.length == 2)return a[1];
        return""
    },fc = function() {
        window.__google_ad_urls || (window.__google_ad_urls = new W(window));
        return window.__google_ad_urls
    };
    q("Goog_AdSense_getAdAdapterInstance", fc);
    q("Goog_AdSense_OsdAdapter", W);
    q("Goog_AdSense_OsdAdapter.prototype.numBlocks", W.prototype.g);
    q("Goog_AdSense_OsdAdapter.prototype.findBlocks", W.prototype.e);
    q("Goog_AdSense_OsdAdapter.prototype.getOsdMode", W.prototype.getOsdMode);
    q("Goog_AdSense_OsdAdapter.prototype.getEid", W.prototype.getEid);
    q("Goog_AdSense_OsdAdapter.prototype.getCorrelator", W.prototype.getCorrelator);
    q("Goog_AdSense_OsdAdapter.prototype.setValidAdBlockTypes", W.prototype.setValidAdBlockTypes);
    q("Goog_AdSense_OsdAdapter.prototype.setupOsd", W.prototype.setupOsd);
    q("Goog_AdSense_OsdAdapter.prototype.registerAdBlockByType", W.prototype.registerAdBlockByType);
    var gc = function(a, b) {
        var c = a.nodeType == 9 ? a : a.ownerDocument || a.document;
        if (c.defaultView && c.defaultView.getComputedStyle)if (a = c.defaultView.getComputedStyle(a, ""))return a[b];
        return h
    },hc = function(a, b) {
        return gc(a, b) || (a.currentStyle ? a.currentStyle[b] : h) || a.style[b]
    },ic = function(a, b, c, d) {
        if (/^\d+px?$/.test(b))return parseInt(b, 10); else {
            var e = a.style[c],f = a.runtimeStyle[c];
            a.runtimeStyle[c] = a.currentStyle[c];
            a.style[c] = b;
            b = a.style[d];
            a.style[c] = e;
            a.runtimeStyle[c] = f;
            return b
        }
    },jc = function(a) {
        var b =
                a.nodeType == 9 ? a : a.ownerDocument || a.document,c = "";
        if (b.createTextRange) {
            c = b.body.createTextRange();
            c.moveToElementText(a);
            c = c.queryCommandValue("FontName")
        }
        if (!c) {
            c = hc(a, "fontFamily");
            if (B && Ia)c = c.replace(/ \[[^\]]*\]/, "")
        }
        a = c.split(",");
        if (a.length > 1)c = a[0];
        return ua(c, "\"'")
    },kc = /[^\d]+$/,lc = function(a) {
        return(a = a.match(kc)) && a[0] || h
    },mc = {cm:1,"in":1,mm:1,pc:1,pt:1},nc = {em:1,ex:1},oc = function(a) {
        var b = hc(a, "fontSize"),c = lc(b);
        if (b && "px" == c)return parseInt(b, 10);
        if (C)if (c in mc)return ic(a, b, "left",
                "pixelLeft"); else if (a.parentNode && a.parentNode.nodeType == 1 && c in nc) {
            a = a.parentNode;
            c = hc(a, "fontSize");
            return ic(a, b == c ? "1em" : b, "left", "pixelLeft")
        }
        c = Sa("span", {style:"visibility:hidden;position:absolute;line-height:0;padding:0;margin:0;border:0;height:1em;"});
        Ua(a, c);
        b = c.offsetHeight;
        c && c.parentNode && c.parentNode.removeChild(c);
        return b
    };
    var X,Y = {};

    function pc(a) {
        if (a == 1)return g;
        return!Y[a]
    }

    function qc(a, b) {
        if (!(!a || a == ""))if (b == 1)if (Y[b])Y[b] += "," + a; else Y[b] = a; else Y[b] = a
    }

    function rc() {
        var a = [];
        L(Y, function(b) {
            a.push(b)
        });
        return a.join(",")
    }

    function sc(a, b) {
        if (o(a))for (var c = 0; c < a.length; c++)p(a[c]) && qc(a[c], b)
    }

    var Z = j;

    function tc(a, b) {
        var c = "script";
        Z = uc(a, b);
        if (!Z)a.google_allow_expandable_ads = j;
        var d = !vc();
        Z && d && b.write("<" + c + ' src="http://' + fb + '/pagead/expansion_embed.js"></' + c + ">");
        a = bc(a, b, I("1", 0.01));
        (d = d || a) && nb("msie") && !window.opera ? b.write("<" + c + ' src="http://' + fb + '/pagead/render_ads.js"></' + c + ">") : b.write("<" + c + '>google_protectAndRun("ads_core.google_render_ad", google_handleError, google_render_ad);</' + c + ">")
    }

    function $(a) {
        return a != h ? '"' + a + '"' : '""'
    }

    function wc(a) {
        var b = "google_unique_id";
        if (a[b])++a[b]; else a[b] = 1;
        return a[b]
    }

    var xc = function(a, b) {
        var c = b.slice(-1),d = c == "?" || c == "#" ? "" : "&",e = [b];
        b = function(f, i) {
            if (f || f === 0 || f === j) {
                if (typeof f == "boolean")f = f ? 1 : 0;
                jb(e, d, i, "=", M(f));
                d = "&"
            }
        };
        L(a, b);
        return e.join("")
    };

    function yc() {
        var a = C && F("6"),b = Ca && F("1.8.1"),c = D && F("525");
        if (Ha && (a || b || c))return g; else if (Ga && (c || b))return g; else if (Ia && b)return g;
        return j
    }

    function vc() {
        return(typeof ExpandableAdSlotFactory == "function" || typeof ExpandableAdSlotFactory == "object") && typeof ExpandableAdSlotFactory.createIframe == "function"
    }

    function uc(a, b) {
        if (a.google_allow_expandable_ads === j || !b.body || a.google_ad_output != "html" || yb(a, b) || !zc(a) || isNaN(a.google_ad_height) || isNaN(a.google_ad_width) || !yc())return j;
        return g
    }

    function zc(a) {
        var b = a.google_ad_format;
        if (O(b))return j;
        if (N(a) && b != "468x15_0ads_al")return j;
        return g
    }

    function Ac() {
        var a;
        if (K.google_ad_output == "html" && !(N(K) || O(K.google_ad_format)) && pc(0)) {
            a = ["6083035","6083034"];
            a = P(a, I("0", 0));
            qc(a, 0)
        }
        return a == "6083035"
    }

    function Bc(a, b) {
        if ((a.google_unique_id || 0) != 0 || O(a.google_ad_format))return"";
        var c = "";
        a = N(a);
        if (b == "html" || a)c = P(["36815001","36815002"], I("0.01", 0));
        if (c == "" && (b == "js" || a))c = P(["36815003","36815004"], I("0.01", 0));
        if (c == "" && (b == "html" || b == "js"))c = P(["36813005","36813006"], I("0.008", 0));
        return c
    }

    function Cc() {
        var a = fc(),b = window.google_enable_osd,c;
        if (b === g) {
            c = "36813006";
            Dc(c, a)
        } else if (b !== j && pc(0))if (c = Bc(window, window.google_ad_output))Dc(c, a); else c = a.getEid();
        if (c) {
            qc(c, 0);
            return c
        }
        return""
    }

    function Dc(a, b) {
        var c = b.getOsdMode(),d = [];
        switch (a) {
            case "36815004":
                c = 1;
                d = ["js"];
                break;
            case "36815002":
                c = 1;
                d = ["html"];
                break;
            case "36813006":
                c = 0;
                d = ["html","js"];
                break
        }
        d.length > 0 && b.setValidAdBlockTypes(d);
        b.setupOsd(c, a, window.google_correlator)
    }

    function Ec(a, b, c, d) {
        var e = wc(a);
        c = xc({ifi:e}, c);
        c = c.substring(0, 1992);
        c = c.replace(/%\w?$/, "");
        var f = "script";
        if ((a.google_ad_output == "js" || a.google_ad_output == "json_html") && (a.google_ad_request_done || a.google_radlink_request_done))b.write("<" + f + ' language="JavaScript1.1" src=' + $(k(c)) + "></" + f + ">"); else if (a.google_ad_output == "html")if (Z && vc()) {
            b = a.google_container_id || d || h;
            a["google_expandable_ad_slot" + e] = ExpandableAdSlotFactory.createIframe("google_ads_frame" + e, k(c), a.google_ad_width, a.google_ad_height,
                    b)
        } else {
            e = '<iframe name="google_ads_frame" width=' + $(a.google_ad_width) + " height=" + $(a.google_ad_height) + " frameborder=" + $(a.google_ad_frameborder) + " src=" + $(k(c)) + ' marginwidth="0" marginheight="0" vspace="0" hspace="0" allowtransparency="true" scrolling="no"></iframe>';
            a.google_container_id ? Fc(a.google_container_id, b, e) : b.write(e)
        }
        return c
    }

    function Gc(a) {
        Rb(a)
    }

    function Hc(a) {
        var b = X.a("ac1") == "44901217";
        if (!Ic(b))return j;
        b = Ac();
        var c = dc(window, window.google_unique_id || 0, X);
        a = Jc(a);
        b = c + Kc(a.google_ad_format, b);
        window.google_ad_url = xc(a, b);
        return g
    }

    var Nc = function(a) {
        a.dt = aa;
        var b = window.google_prev_ad_formats_by_region,c = window.google_ad_section,d = window.google_ad_format,e = window.google_ad_slot;
        if (b[c])if (!O(d)) {
            a.prev_fmts = b[c];
            if (window.google_num_slots_by_client.length > 1)a.slot = window.google_num_slots_by_client[Q]
        }
        var f = window.google_prev_ad_slotnames_by_region;
        if (f[c])a.prev_slotnames = f[c].toLowerCase();
        if (d) {
            if (!O(d))if (b[c])b[c] += "," + d; else b[c] = d
        } else if (e)if (f[c])f[c] += "," + e; else f[c] = e;
        a.correlator = window.google_correlator;
        if (window.google_new_domain_checked ==
                1 && window.google_new_domain_enabled == 0)a.dblk = 1;
        if (window.google_ad_channel) {
            b = window.google_num_slots_by_channel;
            c = "";
            d = window.google_ad_channel.split(Lc);
            for (e = 0; e < d.length; e++) {
                f = d[e];
                if (b[f])c += f + "+"; else b[f] = 1
            }
            a.pv_ch = c
        }
        if (window.google_ad_host_channel) {
            b = Mc(window.google_ad_host_channel, window.google_viewed_host_channels);
            a.pv_h_ch = b
        }
        if (Xa)a.jscb = 1;
        if (Ya)a.jscd = 1;
        a.frm = window.google_iframing;
        b = Vb();
        a.ga_vid = b.vid;
        a.ga_sid = b.sid;
        a.ga_hid = b.hid;
        a.ga_fc = b.from_cookie;
        a.ga_wpids = window.google_analytics_uacct
    },
            Oc = function(a) {
                var b = g;
                if (b = rb(b)) {
                    a.biw = b.width;
                    a.bih = b.height
                }
            },Pc = function(a) {
        var b = tb(window);
        if (b != 0)a.ifk = b.toString()
    };

    function Mc(a, b) {
        var c = a.split("|");
        a = -1;
        for (var d = [],e = 0; e < c.length; e++) {
            var f = c[e].split(Lc);
            b[e] || (b[e] = {});
            for (var i = "",m = 0; m < f.length; m++) {
                var z = f[m];
                if (z != "")if (b[e][z])i += "+" + z; else b[e][z] = 1
            }
            i = i.slice(1);
            d[e] = i;
            if (i != "")a = e
        }
        b = "";
        if (a > -1) {
            for (e = 0; e < a; e++)b += d[e] + "|";
            b += d[a]
        }
        return b
    }

    function Qc() {
        window.google_page_level_exp_state = new V("1", "ac1");
        var a = ["44901211","44901215","44901216","44901212"];
        window.google_page_level_exp_state.d(a, I("0", 0), "1");
        a = ["44901217","44901218"];
        window.google_page_level_exp_state.d(a, I("0", 0), "ac1")
    }

    function Rc() {
        0 == (window.google_unique_id || 0) && Qc();
        X = Zb(window.google_page_level_exp_state);
        Cc();
        var a = Math.random() < 0.01,b = h,c = "";
        if (a) {
            c = "google_temp_span";
            b = Sc(c)
        }
        a = Hc(b);
        b && b.id == c && (b && b.parentNode ? b.parentNode.removeChild(b) : h);
        if (a) {
            b = Ec(window, document, window.google_ad_url);
            fc().registerAdBlockByType(window.google_ad_output, b, j);
            Gc(window)
        }
    }

    var Tc = function(a) {
        L(vb, function(b, c) {
            a[b] = window[c]
        });
        L(ub, function(b, c) {
            a[b] = window[c]
        });
        L(wb, function(b, c) {
            a[b] = window[c]
        })
    },Uc = function(a) {
        sc(window.google_eids, 1);
        a.eid = rc();
        var b = X.o();
        if (a.eid.length > 0 && b.length > 0)a.eid += ",";
        a.eid += b
    };

    function Vc(a, b, c, d) {
        a = Lb(a, b, c, d);
        tc(window, document);
        return a
    }

    function Wc() {
        Qb()
    }

    function Xc(a) {
        var b = {};
        a = a.split("?");
        a = a[a.length - 1].split("&");
        for (var c = 0; c < a.length; c++) {
            var d = a[c].split("=");
            if (d[0])try {
                b[d[0].toLowerCase()] = d.length > 1 ? window.decodeURIComponent ? decodeURIComponent(d[1].replace(/\+/g, " ")) : unescape(d[1]) : ""
            } catch(e) {
            }
        }
        return b
    }

    function Yc() {
        var a = window,b = Xc(document.URL);
        if (b.google_ad_override) {
            a.google_ad_override = b.google_ad_override;
            a.google_adtest = "on"
        }
    }

    function Fc(a, b, c) {
        if (a)if ((a = b.getElementById(a)) && c && c.length != "") {
            a.style.visibility = "visible";
            a.innerHTML = c
        }
    }

    var Kc = function(a, b) {
        return a = O(a) ? "/pagead/sdo?" : b ? "/pagead/render_iframe_ads.html#" : "/pagead/ads?"
    },Zc = function(a, b) {
        b.dff = jc(a);
        b.dfs = oc(a)
    },$c = function(a) {
        a.ref = window.google_referrer_url;
        a.loc = window.google_page_location
    },Ic = function(a) {
        var b = window.google_prev_ad_formats_by_region,c = window.google_prev_ad_slotnames_by_region,d = window.google_ad_section;
        if (O(window.google_ad_format)) {
            window.google_num_sdo_slots += 1;
            if (!a && window.google_num_sdo_slots > 4)return j
        } else if (N(window)) {
            window.google_num_0ad_slots +=
                    1;
            if (!a && window.google_num_0ad_slots > 3)return j
        } else {
            window.google_num_ad_slots += 1;
            if (window.google_num_slots_to_rotate) {
                Nb(1);
                b[d] = h;
                c[d] = h;
                if (window.google_num_slot_to_show == h)window.google_num_slot_to_show = (new Date).getTime() % window.google_num_slots_to_rotate + 1;
                if (window.google_num_slot_to_show != window.google_num_ad_slots)return j
            } else if (!a && window.google_num_ad_slots > 6 && d == "")return j
        }
        a = window.google_num_slots_by_client;
        if (a[Q])a[Q] += 1; else {
            a[Q] = 1;
            a.length += 1
        }
        return g
    },Jc = function(a) {
        var b =
        {};
        Tc(b);
        Nc(b);
        qb(b);
        a && Zc(a, b);
        Oc(b);
        Pc(b);
        Uc(b);
        $c(b);
        b.fu = Mb;
        return b
    },Sc = function(a) {
        var b = window.google_container_id && Na(window.google_container_id) || Na(a);
        if (!b && !window.google_container_id && a) {
            document.write("<span id=" + a + "></span>");
            b = Na(a)
        }
        return b
    },Lc = /[+, ]/;
    window.google_render_ad = Rc;
    function ad() {
        if (Za && typeof K.alternateShowAds == "function")K.alternateShowAds.call(h); else {
            Yc();
            Jb("show_ads.google_init_globals", Vc, Wc);
            tc(window, document)
        }
    }

    Jb("show_ads.main", Lb, ad);
})()