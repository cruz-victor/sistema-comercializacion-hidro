import { Pipe, PipeTransform } from '@angular/core';
import { formatNumber } from "@angular/common";

@Pipe({
  name: 'm3'
})
export class M3Pipe implements PipeTransform {
  transform(value: any, args?: any): any {
    var strMontillo = formatNumber(+value, 'en-US', '1.0-0') + ' M3'
    return strMontillo;
  }
}

