import { Injectable } from '@angular/core';
import { NgbDatepickerI18n, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

const I18N_VALUES = {
      weekdays: ['Lu', 'Ma', 'Mi', 'Ju', 'Vi', 'Sa', 'Do'],
      months: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
};

@Injectable()
export class NgbDatepickerCustomI18n extends NgbDatepickerI18n {

  constructor() {
    super();
  }

  getWeekdayShortName(weekday: number): string {
    return I18N_VALUES.weekdays[weekday - 1];
  }
  getMonthShortName(month: number): string {
    return I18N_VALUES.months[month - 1];
  }
  getMonthFullName(month: number): string {
    return this.getMonthShortName(month);
  }

  getDayAriaLabel(date: NgbDateStruct): string {
    return `${date.day}/${date.month}/${date.year}`;
  }
}