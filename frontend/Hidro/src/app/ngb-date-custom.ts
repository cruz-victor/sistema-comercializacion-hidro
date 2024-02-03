import { NgbDateParserFormatter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import { Injectable } from '@angular/core';
 
@Injectable()
export class NgbDateCustomParserFormatter extends NgbDateParserFormatter {
  parse(value: string): NgbDateStruct{
    if (value) {
        const dateParts = value.trim().split('/');
        return {day: +dateParts[0], month: +dateParts[1], year: +dateParts[2]};
    }
    return <any>null;
  }
 
  format(date: NgbDateStruct): string {
    return (date)?this.leftZeroing(date.day,2)+'/'+this.leftZeroing(date.month,2)+'/'+date.year:''
  }

  private leftZeroing(valor: number, times: number){
    var zeros: String = '';
    for (let i = 0; i < times; i++) zeros = zeros.concat('0');
    zeros = zeros.concat(valor.toString());
    return zeros.substr(zeros.length-times, zeros.length-1)
  }
}
