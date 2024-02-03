import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'leftZero'
})
export class LeftZeroPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    if (value != null){
      if (args != null) {
        var times: number = +args;
        var zeros: String = '';
        for (let i = 0; i < times; i++) 
          zeros = zeros.concat('0');
        zeros = zeros.concat(value.toString());
        return zeros.substr(zeros.length-times, zeros.length-1)
      }    
      return value.toString();
    }
  }

}
