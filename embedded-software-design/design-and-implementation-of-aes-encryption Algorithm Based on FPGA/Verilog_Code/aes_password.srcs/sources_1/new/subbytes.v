`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/10/26 20:22:19
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
//S盒替换subbytes的模块声明
module subbytes(
    data,
    sb
    );

    //接收S盒替换前的数据
    input [127:0] data;
    //接收S盒替换后的数据
    output [127:0] sb;

    //实例化16个 sbox 模块，每个sbox负责替换8位的数据
     sbox q0( 
     .a(data[127:120]),
     .c(sb[127:120]) 
     );
     
     sbox q1( 
     .a(data[119:112]),
     .c(sb[119:112]) 
     );
     
     sbox q2( 
     .a(data[111:104]),
     .c(sb[111:104]) 
     );
     
     sbox q3( 
     .a(data[103:96]),
     .c(sb[103:96]) 
     );
     
     
     sbox q4( 
     .a(data[95:88]),
     .c(sb[95:88])
     );
     
     sbox q5( 
     .a(data[87:80]),
     .c(sb[87:80]) 
     );
     
     sbox q6( 
     .a(data[79:72]),
     .c(sb[79:72]) 
     );
     
     sbox q7( 
     .a(data[71:64]),
     .c(sb[71:64]) 
     );
     
     
     sbox q8( 
     .a(data[63:56]),
     .c(sb[63:56]) 
     );
     
     sbox q9( 
     .a(data[55:48]),
     .c(sb[55:48])
      );
      
     sbox q10(
     .a(data[47:40]),
     .c(sb[47:40])
      );
      
     sbox q11(
     .a(data[39:32]),
     .c(sb[39:32]) 
     );
     
     
     sbox q12(
     .a(data[31:24]),
     .c(sb[31:24]) 
     );
     
     sbox q13(
     .a(data[23:16]),
     .c(sb[23:16]) 
     );
     
     sbox q14(
     .a(data[15:8]),
     .c(sb[15:8]) 
     );
     
     sbox q16(
     .a(data[7:0]),
     .c(sb[7:0])
      );
	  
endmodule
