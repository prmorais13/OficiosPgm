	function maiusculo(texto){       
    	texto.value = texto.value.toUpperCase();    
	}
	
	function abrir(filename, alt, lar){
   		var janelaPop = window.open(filename, "janela", "height=" + alt 
      	+ ",width=" + lar + ",scrollbars=no");

    	if(janelaPop.document.close()){
      		janelaPop.document.close();
    	}
	}
	
	function abrirRel(){
		window.open("exibeRelatorios.xhtml", "janela", "height=800,width=1200,scrollbars=yes");
	}
