`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/10/26 20:18:45
// Design Name: 
// Module Name: test
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////
//keygeneration密钥拓展模块的声明
module keygeneration(
    rc,
    keyin,
    keyout
    );
   
   //储存轮常数（进行到第几轮了）
   input [3:0] rc;
   //本轮用于加密的密钥
   input [127:0]keyin;
   //输出的扩展密钥
   output [127:0] keyout;
   
   wire [31:0] w0,w1,w2,w3,tem;
         
       //将原始密钥 keyin 分割成四个32位的数据        
       assign w0 = keyin[127:96];
       assign w1 = keyin[95:64];
       assign w2 = keyin[63:32];
       assign w3 = keyin[31:0];
       
       //计算扩展密钥 keyout 的每个32位数据，使用异或操作（这里的公式是使用AES密钥扩展算法的公式来计算的）
       assign keyout[127:96]= w0 ^ tem ^ rcon(rc);
       assign keyout[95:64] = w0 ^ tem ^ rcon(rc)^ w1;
       assign keyout[63:32] = w0 ^ tem ^ rcon(rc)^ w1 ^ w2;
       assign keyout[31:0]  = w0 ^ tem ^ rcon(rc)^ w1 ^ w2 ^ w3;
       
       //对密钥进行的s盒变换
       sbox a1
       (
       .a(w3[23:16]),
       .c(tem[31:24])
       );
       
       sbox a2(
       .a(w3[15:8]),
       .c(tem[23:16])
       );
       
       sbox a3(
       .a(w3[7:0]),
       .c(tem[15:8])
       );
       
       sbox a4(
       .a(w3[31:24]),
       .c(tem[7:0])
       );
       
       
     //这个函数是根据轮常数rc来决定使用哪个rcon来进行密钥扩展  
     function [31:0]	rcon;
      input	[3:0]	rc;
      case(rc)	
         4'h0: rcon=32'h01_00_00_00;
         4'h1: rcon=32'h02_00_00_00;
         4'h2: rcon=32'h04_00_00_00;
         4'h3: rcon=32'h08_00_00_00;
         4'h4: rcon=32'h10_00_00_00;
         4'h5: rcon=32'h20_00_00_00;
         4'h6: rcon=32'h40_00_00_00;
         4'h7: rcon=32'h80_00_00_00;
         4'h8: rcon=32'h1b_00_00_00;
         4'h9: rcon=32'h36_00_00_00;
         default: rcon=32'h00_00_00_00;
       endcase

     endfunction

endmodule
