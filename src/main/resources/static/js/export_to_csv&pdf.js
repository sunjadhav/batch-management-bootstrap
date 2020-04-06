$(document).ready(function() {

  function exportTableToCSV($table, filename) {

    var $rows = $table.find('tr:has(th,td)'),

      // Temporary delimiter characters unlikely to be typed by keyboard
      // This is to avoid accidentally splitting the actual contents
      tmpColDelim = String.fromCharCode(11), // vertical tab character
      tmpRowDelim = String.fromCharCode(0), // null character

      // actual delimiter characters for CSV format
      colDelim = '","',
      rowDelim = '"\r\n"',

      // Grab text from table into CSV formatted string
      csv = '"' + $rows.map(function(i, row) {
        var $row = $(row),
          $cols = $row.find('th, td');

        return $cols.map(function(j, col) {
          var $col = $(col),
            text = $col.text();

          return text.replace(/"/g, '""'); // escape double quotes

        }).get().join(tmpColDelim);

      }).get().join(tmpRowDelim)
      .split(tmpRowDelim).join(rowDelim)
      .split(tmpColDelim).join(colDelim) + '"';

    // Deliberate 'false', see comment below
    if (false && window.navigator.msSaveBlob) {

      var blob = new Blob([decodeURIComponent(csv)], {
        type: 'text/csv;charset=utf8'
      });

      // Crashes in IE 10, IE 11 and Microsoft Edge
      // See MS Edge Issue #10396033
      // Hence, the deliberate 'false'
      // This is here just for completeness
      // Remove the 'false' at your own risk
      window.navigator.msSaveBlob(blob, filename);

    } else if (window.Blob && window.URL) {
      // HTML5 Blob        
      var blob = new Blob([csv], {
        type: 'text/csv;charset=utf-8'
      });
      var csvUrl = URL.createObjectURL(blob);

      $(this)
        .attr({
          'download': filename,
          'href': csvUrl
        });
    } else {
      // Data URI
      var csvData = 'data:application/csv;charset=utf-8,' + encodeURIComponent(csv);

      $(this)
        .attr({
          'download': filename,
          'href': csvData,
          'target': '_blank'
        });
    }
  }
////////////////////////////////////////// Student List CSV and PDF ///////////////////////////////////////
  // This must be a hyperlink
  $(".studentsCSV").on('click', function(event) {
    // CSV
	 // var batchName = document.getElementById("tblStudents").rows[1].cells.namedItem("batch").innerHTML;
	  var studentName = document.getElementById("txtSearchStudentName").value;
	  var batchName = document.getElementById("txtSearchBatchName").value;
	  var fileName;
	  var today = new Date();
	  var dateString = today.format("dd-mm-yyyy_hh-MM");
	  
	  if(batchName == ''){
		  fileName = studentName.concat('_Students_'+dateString+'.csv');
	  }
	  else if(batchName != '' && studentName != ''){
		  fileName = batchName.concat('_'+studentName+'_Students_'+dateString+'.csv');
	  }
	  else{
		  fileName = batchName.concat('_Student_List_'+dateString+'.csv');
	  }
	  
    var args = [$('#dvData>table'), fileName];

    exportTableToCSV.apply(this, args);

    // If CSV, don't do event.preventDefault() or return false
    // We actually need this to be a typical hyperlink
  });
  
  
  $(".studentsPDF").on('click', function(event) {
	    // CSV
	  var studentName = document.getElementById("txtSearchStudentName").value;
	  var batchName = document.getElementById("txtSearchBatchName").value;
	  var fileName;
	  var today = new Date();
	  var dateString = today.format("dd-mm-yyyy_hh-MM");
	  
	  if(batchName == ''){
		  fileName = studentName.concat('_Students_'+dateString+'.pdf');
	  }
	  else if(batchName != '' && studentName != ''){
		  fileName = batchName.concat('_'+studentName+'_Students_'+dateString+'.pdf');
	  }
	  else{
		  fileName = batchName.concat('_Student_List_'+dateString+'.pdf');
	  }
	   
		  html2canvas(document.getElementById('tblStudents'), {
		        onrendered: function (canvas) {
		            var data = canvas.toDataURL();
		            var docDefinition = {
		                content: [{
		                    image: data,
		                    width: 500
		                }]
		            };
		            pdfMake.createPdf(docDefinition).download(fileName);
		        }
		    });
	  });
  
///////////////////////////////////////////////////////////////////////////////////////////////////
  
//////////////////////////////////////////Batch List CSV and PDF ///////////////////////////////////////
  // This must be a hyperlink
  $(".batchesCSV").on('click', function(event) {
    // CSV
	  var batchType = document.getElementById("drpBatchType").value;
	  var batchName = document.getElementById("txtBatchName").value;
	  var fileName;
	  var today = new Date();
	  var dateString = today.format("dd-mm-yyyy_hh-MM");
	  
	  if(batchName == ''){
		  fileName = batchType.concat('_Batches_'+dateString+'.csv');}
	  else{
		  fileName = batchName.concat('_'+dateString+'.csv');}
       
    var args = [$('#dvData>table'), fileName];

    exportTableToCSV.apply(this, args);

    // If CSV, don't do event.preventDefault() or return false
    // We actually need this to be a typical hyperlink
  });
  
  
  $(".batchesPDF").on('click', function(event) {
	    // CSV
		  var batchType = document.getElementById("drpBatchType").value;
		  var batchName = document.getElementById("txtBatchName").value;
		  var fileName;
		  var today = new Date();
		  var dateString = today.format("dd-mm-yyyy_hh-MM");
		  
		  if(batchName == '')
			  fileName = batchType.concat('_Batches_'+dateString+'.pdf');
		  else
			  fileName = batchName.concat('_'+dateString+'.pdf');
	      
	       
		  html2canvas(document.getElementById('tblBatches'), {
		        onrendered: function (canvas) {
		            var data = canvas.toDataURL();
		            var docDefinition = {
		                content: [{
		                    image: data,
		                    width: 500
		                }]
		            };
		            pdfMake.createPdf(docDefinition).download(fileName);
		        }
		    });
	  });
  
///////////////////////////////////////////////////////////////////////////////////////////////////
 
 
///////////////////////////////////////// Active Batches CSV and PDF /////////////////////////////
  
//This must be a hyperlink
  $(".activeBatchesCSV").on('click', function(event) {
    // CSV
	  var today = new Date();
	  var dateString = today.format("dd-mm-yyyy_hh-MM");
	  
	  var fileName = "Active_Batches_"+dateString+".csv";
       
    var args = [$('#dvData>table'), fileName];

    exportTableToCSV.apply(this, args);

    // If CSV, don't do event.preventDefault() or return false
    // We actually need this to be a typical hyperlink
  });
  
  
  $(".activeBatchesPDF").on('click', function(event) {
	    // CSV
	  var today = new Date();
	  var dateString = today.format("dd-mm-yyyy_hh-MM");
	  	var fileName = "Active_Batches_"+dateString+".pdf";
	       
		  html2canvas(document.getElementById('tblActiveBatches'), {
		        onrendered: function (canvas) {
		            var data = canvas.toDataURL();
		            var docDefinition = {
		                content: [{
		                    image: data,
		                    width: 500
		                }]
		            };
		            pdfMake.createPdf(docDefinition).download(fileName);
		        }
		    });
	  });
 
///////////////////////////////////////////////////////////////////////////////////////////////////////////  
  
///////////////////////////////////////// Archived Batches CSV and PDF /////////////////////////////
  
//This must be a hyperlink
  $(".archivedBatchesCSV").on('click', function(event) {
    // CSV
	  var today = new Date();
	  var dateString = today.format("dd-mm-yyyy_hh-MM");
	  
	  var fileName = "Archived_Batches_"+dateString+".csv";
       
    var args = [$('#dvData>table'), fileName];

    exportTableToCSV.apply(this, args);

    // If CSV, don't do event.preventDefault() or return false
    // We actually need this to be a typical hyperlink
  });
  
  
  $(".archivedBatchesPDF").on('click', function(event) {
	    // CSV
	  var today = new Date();
	  var dateString = today.format("dd-mm-yyyy_hh-MM");
	  	var fileName = "Archived_Batches_"+dateString+".pdf";
	       
		  html2canvas(document.getElementById('tblArchivedBatches'), {
		        onrendered: function (canvas) {
		            var data = canvas.toDataURL();
		            var docDefinition = {
		                content: [{
		                    image: data,
		                    width: 500
		                }]
		            };
		            pdfMake.createPdf(docDefinition).download(fileName);
		        }
		    });
	  });
  
   
///////////////////////////////////////////////////////////////////////////////////////////////////////////  
    
});

var dateFormat = function () {
	var	token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
		timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
		timezoneClip = /[^-+\dA-Z]/g,
		pad = function (val, len) {
			val = String(val);
			len = len || 2;
			while (val.length < len) val = "0" + val;
			return val;
		};

	// Regexes and supporting functions are cached through closure
	return function (date, mask, utc) {
		var dF = dateFormat;

		// You can't provide utc if you skip other args (use the "UTC:" mask prefix)
		if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
			mask = date;
			date = undefined;
		}

		// Passing date through Date applies Date.parse, if necessary
		date = date ? new Date(date) : new Date;
		if (isNaN(date)) throw SyntaxError("invalid date");

		mask = String(dF.masks[mask] || mask || dF.masks["default"]);

		// Allow setting the utc argument via the mask
		if (mask.slice(0, 4) == "UTC:") {
			mask = mask.slice(4);
			utc = true;
		}

		var	_ = utc ? "getUTC" : "get",
			d = date[_ + "Date"](),
			D = date[_ + "Day"](),
			m = date[_ + "Month"](),
			y = date[_ + "FullYear"](),
			H = date[_ + "Hours"](),
			M = date[_ + "Minutes"](),
			s = date[_ + "Seconds"](),
			L = date[_ + "Milliseconds"](),
			o = utc ? 0 : date.getTimezoneOffset(),
			flags = {
				d:    d,
				dd:   pad(d),
				ddd:  dF.i18n.dayNames[D],
				dddd: dF.i18n.dayNames[D + 7],
				m:    m + 1,
				mm:   pad(m + 1),
				mmm:  dF.i18n.monthNames[m],
				mmmm: dF.i18n.monthNames[m + 12],
				yy:   String(y).slice(2),
				yyyy: y,
				h:    H % 12 || 12,
				hh:   pad(H % 12 || 12),
				H:    H,
				HH:   pad(H),
				M:    M,
				MM:   pad(M),
				s:    s,
				ss:   pad(s),
				l:    pad(L, 3),
				L:    pad(L > 99 ? Math.round(L / 10) : L),
				t:    H < 12 ? "a"  : "p",
				tt:   H < 12 ? "am" : "pm",
				T:    H < 12 ? "A"  : "P",
				TT:   H < 12 ? "AM" : "PM",
				Z:    utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
				o:    (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
				S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
			};

		return mask.replace(token, function ($0) {
			return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
		});
	};
}();

// Some common format strings
dateFormat.masks = {
	"default":      "ddd mmm dd yyyy HH:MM:ss",
	shortDate:      "m/d/yy",
	mediumDate:     "mmm d, yyyy",
	longDate:       "mmmm d, yyyy",
	fullDate:       "dddd, mmmm d, yyyy",
	shortTime:      "h:MM TT",
	mediumTime:     "h:MM:ss TT",
	longTime:       "h:MM:ss TT Z",
	isoDate:        "yyyy-mm-dd",
	isoTime:        "HH:MM:ss",
	isoDateTime:    "yyyy-mm-dd'T'HH:MM:ss",
	isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
};

// Internationalization strings
dateFormat.i18n = {
	dayNames: [
		"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
		"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
	],
	monthNames: [
		"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
		"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
	]
};

// For convenience...
Date.prototype.format = function (mask, utc) {
	return dateFormat(this, mask, utc);
};


