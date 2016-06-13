<html>
	<head>
  		<title>Report</title>
	</head>
	<body>
		<img src="PieChart.jpeg" alt="TestResults" style="width:640px;height:480px;" />
		<br />
		<table border="1">
  			<#list headers as header>
				<tr>
					<#list header as string>
  						<th>${string}</th>
    				</#list>
				</tr>
			</#list>
  			<#list rows as row>
				<tr <#if row?last == "passed" || row?last == "PASSED">bgcolor="#d3ffce"<#elseif row?last == "failed" || row?last == "FAILED">bgcolor="#ffc0cb"<#elseif row?last == "n/a" || row?last == "N/A">bgcolor="#b0e0e6"</#if>>
					<#list row as string>
  						<td>${string}</td>
    				</#list>
				</tr>
			</#list>
		</table>
	</body>
</html>