
import java.io.File
import java.io.FileNotFoundException
val stack=mutableListOf<Int>()
var R: Int=0;
var PasJump=0
var PasJumpString=""
var PasReal=0
var PasJumpz=""
var OK=1;
var cmd=""
var label=""
var k=0
var i=0
var com=mutableListOf<String>()
fun main(args: Array<String>){
  val fileName = args[0]
    val file = File(fileName)

    try {
        val bufferedReader = file.bufferedReader()
        val lines = mutableListOf<String>()

        bufferedReader.useLines { lines.addAll(it) }
        //println(lines)
        for (line in lines) {
             val ins=line.split(" ").filter { it != "" }
             i=0
             com.addAll(ins)
        }
        }
     catch (e: FileNotFoundException) {
        println("ERROR unable to open file filename.asm(ENOENT: no such file or directory, open filename.asm)")
    }
    //println(com)
    while(i< com.count()){
         PasReal=PasReal+1  
         if(PasJumpString!=""){
                if(PasJumpString==com[i]){
                    OK=1;
                    PasJumpString=""
                    i++
                }
            }
           if(PasJump!=0){
            if(PasJump==PasReal){
                OK=1
                PasJump=0
            }
          //  if(PasJump<PasReal)
         //  {    PasReal=i-PasJump-k-2
          //      i=i-PasJump-k-2
          //      PasJump=0
          //      OK=1
          //      println(PasJump)
                
           //}
           }
           if(OK==3){
            if(com[i] in listOf("push","pop","load","store","add","sub","mul","div","mod","jump","jumpz","jumpnz","print","stack")){
                OK=1
            }
           }
            if(OK==0){
                if(com[i] in listOf("push","jump","jumpz","jumpnz")){
                    k++
                    i++
                }
            }
    //   println(com[i])
        if(OK==1){
       if(com[i] in listOf("push","pop","load","store")){
                Memory(com[i],com[i+1])   
            }else
            if(com[i] in listOf("nop")){
                NoAction(com[i])
            }else
            if(com[i] in listOf("add","sub","mul","div","mod")){
                Math(com[i])
            }else
            if(com[i] in listOf("jump","jumpz","jumpnz")){
                 Jump(com[i],com[i+1])
                if(PasJump>=com.count()){
                    println("ERROR ($cmd): invalid jump address $PasJump")
                    OK=1
                }
                if(PasJumpString !in com&&PasJumpString!=""){
                     label=com[i]
                    println("ERROR ($cmd): undefined label $label")
                    OK=1
                }
            }else
            if(com[i] in listOf("print","stack")){
                Pseudo(com[i])
            }else{
                val q=com[i]
                    println("ERROR ($q): unknown instruction")
                }
                }
                i++
            }
            
    }

  
    

fun Memory(x: String, y: String){
    when(x){
        "push"->{
                val f=y.toFloat()
                stack.add(f.toInt())
                i++
                k++
        }
        "pop"->{
            if(stack.count()==0){
            println("ERROR ($x): stack underflow")
            }else{
                stack.remove(stack[stack.count()-1])
                }
        }
        "load"->{
            if(stack.count()==0){
        println("ERROR ($x): stack underflow")
    }else{
            R=stack[stack.count()-1]
            stack.remove(stack[stack.count()-1])
    }
        }
        "store"->{if(stack.count()==0){
        println("ERROR ($x): stack underflow")
        }else{
            stack.add(R)
            }
        }
    }
}
fun NoAction(x:  String){
    when(x){
        "nop"->{
            OK=3
        }
    }
}
fun Math(x:String){
    if(stack.count()<=1){
        println("ERROR ($x): stack underflow")
    } else {
    when(x){
        "add"->{
            val s=stack[stack.count()-1]+stack[stack.count()-2]
            stack.remove(stack[stack.count()-1])
            stack.remove(stack[stack.count()-1])
            stack.add(stack.count(),s)
        }
        "sub"->{
            val a=stack[stack.count()-2]-stack[stack.count()-1]
            stack.remove(stack[stack.count()-1])
            stack.remove(stack[stack.count()-1])
            stack.add(stack.count(),a)

        }
        "mul"->{
            val b=stack[stack.count()-1]*stack[stack.count()-2]
            stack.remove(stack[stack.count()-1])
            stack.remove(stack[stack.count()-1])
            stack.add(stack.count(),b)
        }
        "div"->{
             val c=stack[stack.count()-2]/stack[stack.count()-1]
            stack.remove(stack[stack.count()-1])
            stack.remove(stack[stack.count()-1])
            stack.add(stack.count(),c)
        }
        "mod"->{
             val d=stack[stack.count()-2]%stack[stack.count()-1]
            stack.remove(stack[stack.count()-1])
            stack.remove(stack[stack.count()-1])
            stack.add(stack.count(),d)
        }
    }
    }
}
fun Pseudo(x: String){
    when(x){
        "stack"->{
            if(stack.count()==0){
            println("[ ]")
        }else {
            print("[ ")
            for( i in stack)
            {
                print("$i ")
            }
            print("]")
            println()
        }
        }
        "print"->{
            if(stack.count()==0){
                println("ERROR ($x): stack underflow")
            }else {
                println(stack[stack.count()-1])
            }
            
        }
    }
}
fun Jump(x: String,y:String){
    cmd=x;
    i++
    k++
   
    when(x){
        "jump"->{
            if(y[0].isDigit()){
                PasJump=y.toInt()
                OK=0
            }else{
                 PasJumpString=y+":"
                 OK=0
            }
               
            
        }
        "jumpz"->{
            if(stack[0]!=0){
                OK=1
            }else {
                if(y[0].isDigit()){
                PasJump=y.toInt()
                OK=0
            }else{
                 PasJumpString=y+":"
                 OK=0
            }
            }
        }
        "jumpnz"->{
            if(stack[0]==0){
                OK=1
            }else {
                if(y[0].isDigit()){
                PasJump=y.toInt()
                OK=0
            }else{
                 PasJumpString=y+":"
                 OK=0
            }
            }
        }
    }
}
