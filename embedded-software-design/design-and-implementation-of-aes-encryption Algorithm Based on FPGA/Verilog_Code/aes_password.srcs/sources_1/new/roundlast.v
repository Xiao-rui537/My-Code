`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2024/10/26 20:26:04
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
//roundlast��ģ�����������һ�ּ����㷨Ҫ���Ĳ��裩
module roundlast(
    clk,
    rc,
    rst,
    rin,
    keylastin,
    fout
    );
    
    //ʱ���ź�
    input clk;
    input rst;
    //�����ֳ��������е��ڼ����ˣ�
    input [3:0]rc;
    //���һ�����ڼ��ܵ�ԭʼ����
    input [127:0]rin;
    //���һ�����ڼ��ܵ���Կ
    input [127:0]keylastin;
    //���һ�ּ��ܺ�����ļ�������
    output reg [127:0]fout;

    //���ڴ���SubBytes��S���滻����ShiftRows������λ����MixColumns���л�����������
    wire [127:0] sb,sr,mcl,keyout;
    wire [127:0]  fout_comb;

    //�������һ�ֵ���Կ
    keygeneration t0(
        rc,
        keylastin,
        keyout
        );
    
    //����SubBytes��S���滻��    
    subbytes t1(
        rin,
        sb
        );
    
    //����ShiftRows������λ��   
    shiftrow t2(
        sb,
        sr
        );
    //ע�⣺���һ�ֲ���Ҫ����MixColumns���л�����
    
    //�������һ��AddRoundKey������Կ�ӣ����õ����ܵ����ս��   
    assign fout_comb= keyout^sr;
    
    always@(posedge clk or posedge rst)begin
        if(rst)begin
            fout <= 0;
        end
        else begin
            fout <= fout_comb;
        end
    end

endmodule
