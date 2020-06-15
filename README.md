# HelloActivity
- 第一个安卓工程，包含两个页面（Activity）
- 运行AES与SM4的加解密功能，无其他用途
- 布局还有问题，仅针对华为Honor30进行了手工调整
- 程序中的`AES_GEN_KEY`以及`SM4 GEN KEY`只是实现了6位口令的随机生成，而非真正的算法密钥
  + 算法密钥由PBE机制生成
