var sign = "cross";

var currentPlayer = 'Player A';
var gameActive = false;
var roundWon = false;

var button=document.getElementById('start');

var patternX;
var patternY;
var eTarget;
var el;

//const winningMessage = () => ` ${currentPlayer} hat gewonnen!`;
//const drawMessage = () => `Game ended in a draw!`;
//const currentPlayerTurn = () => ` ${currentPlayer} ist am Zug`;



var statusDisplay = document.getElementById("playerStatus").innerHTML = currentPlayer +  " " + "ist am Zug";


patternX = [0, 0, 0, 0, 0, 0, 0, 0, 0];
patternY = [0, 0, 0, 0, 0, 0, 0, 0, 0];



   button.onclick = function(){

    gameActive = true;
     
    onLoadHandler();
   };
  

  function hide(){
    button.style.display="none";
  }

 


function onClickHandler(event) {
     eTarget = event.target || event.srcElement;
     el = eTarget;
    //if (!el.classList.contains("cross") && !el.classList.contains("circle") && gameActive == true) {
        if(gameActive == true){
        el.className += " " + sign

        if (sign == "cross") {
            event.target.innerHTML = '<img src = "https://image.flaticon.com/icons/png/512/57/57165.png">';
            currentPlayer = "Player B";
            statusDisplay = document.getElementById("playerStatus").innerHTML = currentPlayer +  " " + "ist am Zug";



            patternX[parseInt(el.id)] = 1;
        
         
        }
        if (sign == "circle") {
            event.target.innerHTML = '<img src = "https://image.flaticon.com/icons/png/512/16/16894.png">';
            currentPlayer = "Player A";
            statusDisplay = document.getElementById("playerStatus").innerHTML = currentPlayer +  " " + "ist am Zug";


            patternY[parseInt(el.id)] = 1;
        
            
        }

        if (CheckWin(patternX) || CheckWin(patternY)) {
            document.getElementsByClassName("game-over-message").item(0).style.display = "block";
            if(CheckWin(patternX)){
                document.getElementsByClassName("winner-message").item(0).style.display = "block";
                gameActive = false;

            }else if(CheckWin(patternY)){
                document.getElementsByClassName("winner-message2").item(0).style.display = "block";
                gameActive = false;

            }
            gameActive = false;
            roundWon = true;
            //document.getElementById("game").style.opacity = "0.15";
        }else{
            roundWon = false;
        }


        if(checkDraw()){
            document.getElementsByClassName("draw-message").item(0).style.display = "block";
            gameActive = false;
        }

        toggleSign();
    }

}


function checkDraw(){
    
    for (var i=0; i <= 8; i++) {
        
        if(!document.getElementById(i).classList.lenght === 0){
            return true;
        }else{
            return false;
      
   }
   }
}


function CheckWin(pattern) {
       
    var roundWon = false;
    if ((pattern[0] == 1 && pattern[1] == 1 && pattern[2] == 1) ||
        (pattern[3] == 1 && pattern[4] == 1 && pattern[5] == 1) ||
        (pattern[6] == 1 && pattern[7] == 1 && pattern[8] == 1) ||
        (pattern[0] == 1 && pattern[3] == 1 && pattern[6] == 1) ||
        (pattern[1] == 1 && pattern[4] == 1 && pattern[7] == 1) ||
        (pattern[2] == 1 && pattern[5] == 1 && pattern[8] == 1) ||
        (pattern[0] == 1 && pattern[4] == 1 && pattern[8] == 1) ||
        (pattern[2] == 1 && pattern[4] == 1 && pattern[6] == 1)) {
        
            gameActive = false;
            roundWon = true;
        return true;
    } else {
        
        roundWon = false;
        gameActive = true;
       
    }



    

}

function toggleSign() {

    if(gameActive == true){
    if (this.sign == "cross") {
        this.sign = "circle";
    } else {
        this.sign = "cross";
    }
  }
}

function restartGame(){
 sign = "cross";
currentPlayer = 'Player A';
gameActive = true;
document.getElementsByClassName("game-over-message").item(0).style.display = "none";
document.getElementsByClassName("winner-message").item(0).style.display = "none";
document.getElementsByClassName("winner-message2").item(0).style.display = "none";
this.patternX = [];
this.patternY = [];

roundWon = false;

button.style.display="block";

for (var i =0; i <= 8; i++) { 
    clearCell(i);
    }
onLoadHandler()

statusDisplay = document.getElementById("playerStatus").innerHTML = currentPlayer +  " " + "ist am Zug";



}


function clearCell() {
    for (var i=0; i <= 8; i++) {
      document.getElementById( i).innerHTML = "";
    }
  }



function onLoadHandler() {
    if(gameActive == true ){
    var elements = document.getElementsByClassName("cell");
     
    for (var i = 0; i < elements.length; i++) {

        var el = elements.item(i);
        
    
        if (el.addEventListener) {
            el.addEventListener("click", onClickHandler, false);
        }
        else {
            el.attachEvent("onclick", onClickHandler)
        }
    }
}  

   
}
