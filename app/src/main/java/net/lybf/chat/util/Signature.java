package net.lybf.chat.util;

public class Signature
  {
    private Signature[] signatures;
    private StringBuilder builder;

	/*
	 public void getSignature(View view) {
	 String pkgname = et_pkgname.getText().toString();
	 boolean isEmpty = TextUtils.isEmpty(pkgname);
	 if (isEmpty) {
	 Toast.makeText(this, "应用程序的包名不能为空！", Toast.LENGTH_SHORT);
	 } else {
	 try {
	 packageInfo = manager.getPackageInfo(pkgname, PackageManager.GET_SIGNATURES);
	 signatures = packageInfo.signatures;


	 for (Signature signature : signatures) {
	 builder.append(signature.toCharsString());
	 }
	 signature = builder.toString();
	 tv_signature.setText(signature);
	 } catch (NameNotFoundException e) {
	 e.printStackTrace();
	 }
	 }
	 }*/
  }
