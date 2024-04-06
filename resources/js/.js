document.addEventListener('DOMContentLoaded', function() {
		
		$.ajax({
			type:"get",
			 	url:"<%=request.getContextPath() %>/calendar.do",
	 			dataType:"json" ,
	   			success : function(data){
   				console.log(data);
   				
   		    	var calendarEl = document.getElementById('calendar');
   		        var calendar = new FullCalendar.Calendar(calendarEl, {
   		        	
   		        	eventClick: function(info) {
   		        	    alert('Event: ' + info.event.title);
   		        	    alert('date: ' + info.event.start);
 					},
   		            
   		            selectable: true,
   		            selectMirror: true,
   		            
   		            locale: "ko",

   		            dayCellContent: function (info) {
   		                var number = document.createElement("a");
   		                number.classList.add("fc-daygrid-day-number");
   		                number.innerHTML = info.dayNumberText.replace("일", '').replace("日","");
   		                if (info.view.type === "dayGridMonth") {
   		                  return {
   		                    html: number.outerHTML
   		                  };
   		                }
   		                return {
   		                  domNodes: []
   		                };
   		            },
	   		        navLinks: false,
   		            editable: false,
   		            dayMaxEvents: false, // allow "more" link when too many events
   		            fixedWeekCount: false,
   		            
   		            events: data
   		        });
   		        calendar.render();
   			} 
		});
    });