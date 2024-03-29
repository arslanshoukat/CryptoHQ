package com.haroof.common.util

import com.haroof.common.R

private val currencyCodeToCountryMap = mapOf(
  "aed" to R.drawable.ae,
  "ars" to R.drawable.ar,
  "aud" to R.drawable.au,
  "bdt" to R.drawable.bd,
  "bhd" to R.drawable.bh,
  "bmd" to R.drawable.bm,
  "brl" to R.drawable.br,
  "cad" to R.drawable.ca,
  "chf" to R.drawable.ch,
  "clp" to R.drawable.cl,
  "cny" to R.drawable.cn,
  "czk" to R.drawable.cz,
  "dkk" to R.drawable.dk,
  "eur" to R.drawable.eu,
  "gbp" to R.drawable.gb,
  "hkd" to R.drawable.hk,
  "huf" to R.drawable.hu,
  "idr" to R.drawable.id,
  "ils" to R.drawable.il,
  "inr" to R.drawable.`in`,
  "jpy" to R.drawable.jp,
  "krw" to R.drawable.kw,
  "kwd" to R.drawable.kw,
  "lkr" to R.drawable.lk,
  "mmk" to R.drawable.mm,
  "mxn" to R.drawable.mx,
  "myr" to R.drawable.my,
  "ngn" to R.drawable.ng,
  "nok" to R.drawable.no,
  "nzd" to R.drawable.nz,
  "php" to R.drawable.ph,
  "pkr" to R.drawable.pk,
  "pln" to R.drawable.pl,
  "rub" to R.drawable.ru,
  "sar" to R.drawable.sa,
  "sek" to R.drawable.se,
  "sgd" to R.drawable.sg,
  "thb" to R.drawable.th,
  "try" to R.drawable.tr,
  "twd" to R.drawable.tw,
  "uah" to R.drawable.ua,
  "usd" to R.drawable.us,
  "vef" to R.drawable.ve,
  "vnd" to R.drawable.vn,
  "zar" to R.drawable.za,
  "bch" to R.drawable.bch,
  "bits" to R.drawable.btc,
  "bnb" to R.drawable.bnb,
  "btc" to R.drawable.btc,
  "dot" to R.drawable.dot,
  "eos" to R.drawable.eos,
  "eth" to R.drawable.eth,
  "link" to R.drawable.link,
  "ltc" to R.drawable.ltc,
  "sats" to R.drawable.btc,
  "xlm" to R.drawable.xlm,
  "xrp" to R.drawable.xrp,
  "yfi" to R.drawable.yfi,
)

fun getCountryFlagFromCurrencyCode(currencyCode: String): Int {
  return currencyCodeToCountryMap.getOrDefault(currencyCode, R.drawable.flag_placeholder)
}