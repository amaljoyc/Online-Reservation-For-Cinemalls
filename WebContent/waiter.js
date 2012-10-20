/*
Ajax Waiter version 1.0
Copyright (c) 2008, Greg Murray

MIT License

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

*/
window.waiter = function(){

    function getPosition(_e){
        var pX = 0;
        var pY = 0;
        if(_e.offsetParent) {
            while(true){
                pY += _e.offsetTop;
                pX += _e.offsetLeft;
                if(_e.offsetParent == null){
                    break;
                }
                _e = _e.offsetParent;
            }
        } else if(_e.y) {
                pY += _e.y;
                pX += _e.x;
        }
        return {x: pX, y: pY};
  };

  function _show(args) {
      
    var ctx = {
         opacity : 75,
         background : '#FFF',
         textColor : '#000',
         fontSize : '14px', 
         message : 'Please Wait'
    };
      
    if (args) {
       for (var i in args) {
           ctx[i] = args[i];
        }
    }

   if (!ctx.targetId) ctx.targetId = 'body'; 

      if (!document.getElementById(ctx.targetId + '_splash')) {
       var _target = document.getElementById(ctx.targetId);
       if (ctx.targetId == 'body') _target = document.body; 
       if (_target) {
          var _loc = getPosition(_target);
          var _dim = { w: _target.clientWidth, h : _target.clientHeight, scrollY : 0};
          var _cw = _target.clientWidth;

          var _ch = 150;
          if (_target.clientHeight && _target.clientHeight != 0) _ch = _target.clientHeight;

          var _yoffset = 0;        
          if (ctx.targetId == 'body') {
              _dim = getWindowDimensions();            
              _yoffset = _dim.scrollY;
              _ch = _dim.h;
              _cw = _dim.w;           
          }
          var _splashW = 150;
          var _splashH = 125;
          var _iconW = 200;
          var _iconH = 125;
          
          // size the loader
          if (_ch < _iconH) {          
              _splashH = _ch -10;
              _splashW = _splashH;
              _iconH = _splashH - 15;
              _iconW = _iconH;
          }
               
          var _splash = document.createElement('div');      
          _splash.id = ctx.targetId + '_splash';
          _splash.style.position = 'absolute';
          _splash.style.zIndex = 9998;

          document.body.appendChild(_splash);

          // set the transparency
          if (/MSIE/i.test(navigator.userAgent)) {
	           _splash.style.filter = 'alpha(opacity=\"' + ctx.opacity + '\")';
	      } else {
		      _splash.style.opacity = (ctx.opacity / 100);
		  }
           // create the spinner container
           var _icon = document.createElement('div');
           _icon.style.position = 'relative';
           _icon.id = ctx.targetId + '_spinner';

          _splash.appendChild(_icon);
      
          _icon.style.width = _iconW + 'px';
          _icon.style.height = _iconH + 'px';
          if (!ctx.spinner) ctx.spinner = {};
          ctx.spinner.uuid = ctx.targetId + '_spinner';
          window.spinner.start(ctx.spinner);
 
          if (ctx.message) {
              var _message = document.createElement('div');
              _message.id = _splash.id + '_message';
              _message.innerHTML = ctx.message;
              _message.style.color = ctx.textColor;             
              _message.style.position = 'absolute';
              _message.style.textAlign = 'center';
              _message.style.fontSize = ctx.fontSize;                
              _splash.appendChild(_message);             
              _splashH += 25;
              _splashW += 25;      
          }                    
       }

       _splash.style.background = ctx.background;
       if (ctx.targetId == 'body') {

           if (_dim.scrollbarsY == true) {
               _ch = _dim.h + _dim.scrollY;
           }
           if (_dim.scrollbarsX == true) {                 
               _cw = _dim.w + _dim.scrollX;
           }
           // account for scrollbars
           if (_dim.scrollbarsY) {
               if (_dim.scrollY > 0) _cw -=  15;
           }
           if (_dim.scrollbarsX) {
               if (_dim.scrollX > 0) _ch -=  15;
           }                                
           _splash.style.top =  0 + 'px';
           _splash.style.left = 0 + 'px';
           
       } else {
           _splash.style.top =  _loc.y + 1 + 'px';
           _splash.style.left = _loc.x + 1 +'px';
       }
       _splash.style.width = _cw + 'px';	       
       _splash.style.height = _ch + 'px';                 

       _icon.style.left = (_splash.clientWidth / 2) - (_iconW / 2)   +  'px';
       // account for the size half way of the icon and 15 px for the message div
       _icon.style.top = (_dim.scrollY + (_dim.h / 2) - (_iconH / 2)  - 15) +  'px';
       
       if (ctx.message) {           
             _message.style.width = _cw - 2 + 'px';
             // account for the size half way of the icon and 15 px for the message div
             _message.style.top =  (_dim.scrollY + (_dim.h / 2) +  _iconH /2 -15) + 'px';
       }
     }
   };
   
   function _setMessage(args) {
     if (!args) args = {};
     if (!args.targetId) args.targetId = 'body';
         var _target = document.getElementById(args.targetId + '_splash_message');
         if (_target && args.message) {
             _target.innerHTML = args.message;
         }
   };
 
   function _hide(args) {
     window.spinner.stop({uuid : args.targetId + '_spinner'});
     if (!args) args = { targetId : 'body'};
     if (args.targetId) { 
         var _target = document.getElementById(args.targetId + '_splash');
         if (_target) {
             _target.parentNode.removeChild(_target);
         }
     }
   };
   
   function getWindowDimensions() {
       
        var _w = 0;
        var _h = 0;
        var _sx = 0;
        var _sy = 0;
		var _sh = 0;
        var _docHeight;
		
		var hscrollbars = false;
		var vscrollbars = false;
        
        if (document.body && document.body.clientHeight){
        	_docHeight = document.body.clientHeight;
        }
        if (window.innerWidth) {
            _w = window.innerWidth;
            _h = window.innerHeight;
        } else if (document.documentElement &&
            document.documentElement.clientHeight) {      	
            _w = document.documentElement.clientWidth;
            _h = document.documentElement.clientHeight;
			_sh = document.documentElement.scrollHeight - _docHeight;
        } else if (document.body) {    
            _w = document.body.clientWidth;
            _h = document.body.clientHeight;
        }

		if (window.pageYOffset) {
            _sx = window.pageXOffset;
            _sy = window.pageYOffset;
        } else if (document.documentElement &&
            document.documentElement.scrollTop) {
            _sx = document.documentElement.scrollLeft;
            _sy = document.documentElement.scrollTop;            
        } else if (document.body) {
            _sx = document.body.scrollLeft;
            _sy = document.body.scrollTop;
        }
		if ((window.scrollMaxY && window.scrollMaxY >0) ||
		 (document.body.scrollHeight > _h)
		     ) {
			_sh = 38;
			vscrollbars = true;
		}
		if ((window.scrollMaxX && window.scrollMaxX >0) ||
		 (document.body.scrollWidth > _w)
		     ) {         
			hscrollbars = true;
		}      
        
        return {w : _w, h: _h, docHeight :_docHeight,
                scrollX : _sx, scrollY : _sy, scrollbarsX : hscrollbars,  scrollbarsY : vscrollbars, scrollHeight : _sh };
    };

   return {
       show : _show,
       hide : _hide,
       setMessage : _setMessage
   }
}();

window.spinner = function() {

function _spinner () {
  
    function getElipticalPoint(_d, ctx) {
      var _rad = _d * (Math.PI / 180);
      var _x = Math.round(ctx.centerX + ctx.xRadius * Math.sin(_rad));
      var _y =  Math.round(ctx.centerY + ctx.yRadius * Math.cos(_rad));
      return {x : _x, y : _y};
    }
   
    function processBlock(_index, deg, ctx) {

        // if between 0 / 180 we should be shrinking
        var size = 1;      
        if (deg >= 0 && deg < 180) {
            size = (180 - deg) / 180;
        // if between 180 / 359 we should be growing
        } else if (deg >= 180 && deg <= 360) {
            size = (deg-180)/180;
        }

        scaleBlock(_index, size, ctx);
        var pt = getElipticalPoint(deg, ctx);         
        if (ctx.items[_index]) {
            ctx.items[_index].style.zIndex = Math.round(size * 100);
            ctx.items[_index].style.left = ctx.offset.x + pt.x + 'px';
            ctx.items[_index].style.top = ctx.offset.y + pt.y + 'px';   
        }
    }
    
    function scaleBlock(_index, percentage, ctx) {
	    var _size = Math.floor(ctx.maxWidth  * percentage) +  ctx.minWith;
        ctx.items[_index].style.height = _size + 'px';    
	    ctx.items[_index].style.width = _size + 'px';
        
    }

   function doAnimation(forward, ctx) {
  
    if (!ctx.items[0].parentNode || !ctx.items[0].parentNode.animate) {
        return;
    }
    ctx.counter++;
   
    for (var _l = 0;_l < ctx.items.length; _l++) {
        var _ldegree = 0;
        if (ctx.degree + ctx.items[_l].degree > 360) {
             _ldegree = (360 - (ctx.degree + ctx.items[_l].degree)) * -1;
        } else {
            _ldegree =  ctx.degree + ctx.items[_l].degree;
        }
        if (ctx.reverse == _l) {
            ctx.items[_l].style.background = ctx.backgroundSelected;
        } else {
            ctx.items[_l].style.background = ctx.background;
        }
        if (forward && ctx.degree >= 360) {
            ctx.degree = 0;
      	} else if (!forward &&  ctx.degree < 0) {
            ctx.degree = 360;
      	}
        processBlock(_l, _ldegree, ctx);
      }
      
      if (forward) {
          ctx.degree = ctx.degree + ctx.increment;
      } else {
          ctx.degree = ctx.degree - ctx.increment;
      }

      if (ctx.reverse != -1 && ctx.counter % 5 == 0) {
           ctx.reverse--;
          if (ctx.reverse < 0) {
              ctx.reverse = ctx.items.length -1;
          }
       }
      if (ctx.items[0].parentNode.animate) {
          setTimeout(function(){ doAnimation(forward,ctx);}, ctx.delay);
      }       
   };
   
   this.stop = function(args) {
       var container = document.getElementById(args.uuid);
       if (container && container.ritems) {
          for (var i=0; i < container.ritems.length; i++) {
              if (container.ritems[i]) container.ritems[i].parentNode.removeChild(container.ritems[i]);
          }
          container.animate = false; 
       }
          
   };
   
   this.start = function(args) {    
       var container = document.getElementById(args.uuid);
       var ctx = {
           xRadius : 80,  
           yRadius : 30,  
           centerX : 80,
           centerY : 50,
           maxWidth : 15,
           minWith : 10,
           delay : 20,
           increment :  2,
           counter : 0,
           reverse : 0,
           degree : 0,
           count : 9,
           background : '#ABABAB',
           backgroundSelected : '#E6EBFF',
           border : '1px solid #000',
           offset : { x :0, y : 0}
       };

       container.ritems = [];
       // mix in properties
       for (var i in args) {
           ctx[i] = args[i];
       }

       if (!ctx.gap) ctx.gap = (360 / ctx.count);
       
       for (var i=0; i < ctx.count;i++) {
            var d = document.createElement('p');
            d.style.position = 'absolute';
             d.style.background = ctx.background;
            d.style.border = ctx.border;
            d.degree = i * ctx.gap;
            container.ritems.push(d);
            container.appendChild(d);
        }

        ctx.reverse = container.ritems.length -1;
        container.animate = true;
        ctx.items = container.ritems;
        doAnimation(true, ctx, 0);
       
   };
   return this;
}
    return new _spinner();
}();