`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/10/26 19:32:44
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
//这是仿真文件
module AES_TB;

	// 接收时钟信号
	reg clk;
	reg rst=0;
	// 输出加密后的信号
	wire [127:0] tempout;
	// 输出加密前的信号和密钥
	wire [127:0] datain;
	wire [127:0] key;

    parameter CLK_PERIOD = 10;
    
	// 实例化uut
	AES_TOP uut (
		.clk(clk), 
		.rst(rst),
		.datain(datain),
		.key(key),
		.tempout(tempout)
	);

initial begin
    clk = 0;
    repeat(1000) begin
        #(CLK_PERIOD / 2);
        clk = ~clk;
    end
end

initial begin
    #(10) rst =1;
    # 20 rst =0;
end
        
endmodule

