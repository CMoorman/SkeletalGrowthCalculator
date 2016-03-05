package main;

public class FELS_method {

	
	const int TOTAL_INDICATORS = 132;
	const int MAX_VALUES[];
	const double parameters[][];
	int grade[];
	int cubed_ratio[];
	int sex;
	int iterator1;
	int RUNMODE;
	int M1;
	int iterator;
	double U;
	double grade1;
	double grade2;
	double grade3;
	double Q1;
	double Q2;
	double SLOPE;
	double P1Q1;
	double P2Q2;
	double T0;
	double T1;
	double current_estimate;
	double deriv1;
	double deriv2;
	double deriv_holder;
	
	
	
	do{
		iterator1++;

		//Stands for non batch mode
		if(RUNMODE==0){
			println("Iteration %d Estimate now", iterator1, current_estimate);
		}

		T0=T1;
		T1=current_estimate;
		deriv_holder=deriv1;
		deriv1=0;
		deriv2=0;

		for(iterator=FIRST;iterator<=LGRADED;iterator++){

			if(grade[iterator]!=0){

				//SLOPE is d, the rate parameter
				SLOPE=parameters[TOTAL_INDICATORS*(sex-1)+iterator,1];

				// For each possible grade
				for(M1=1;M1<MAX_VALUES[iterator];M1++){

					if(grade[iterator]==M1){

						grade1=1.0;

						if(M1!=1){

							// U is (tau - current estimate)*d
							U=(parameters[TOTAL_INDICATORS*(sex-1)+iterator,M1]-current_estimate)*SLOPE;

							// Correct values that are too large in magnitude
							if(abs(U)>10){

								if(U>10){

									U=10.0;
								}
								
								if(U<-10){

									U=-10.0;
								}
							}

							grade1=1.0/(1.0+exp(U));
						}

						grade2=0.0;

						if(M1!=MAX_VALUES[iterator]){

							U=(parameters[TOTAL_INDICATORS*(sex-1)+iterator,M1+1]-current_estimate)*SLOPE;

							// Correct values that are too large in magnitude
							if(abs(U)>10){

								if(U>10){

									U=10.0;
								}
								
								if(U<-10){

									U=-10.0;
								}
							}
							
							grade2=1.0/(1.0+exp(U));


						}

						grade3=grade1-grade2;

						// Correct values that are too small in magnitude since later you divide by grade3
						if(grade3<0.005){

							grade3=0.005;
						}

						Q1=1.0-grade1;
						Q2=1.0-grade2;
						P1Q1=grade1*Q1;
						P2Q2=grade2*Q2;

						// Add up the first derivatives with respect to current_estimate across graded indicators
						deriv1=deriv1+SLOPE*(P1Q1-P2Q2)/grade3;
						D=(Q1-grade1)*P1Q1 + (grade2-Q2)*P2Q2;
						D=D - sqr(P2Q2-P1Q1)/grade3;

						// Add up the second derivatives with respect to current_estimate across graded indicators
						deriv2=deriv2 - D*sqr(SLOPE)/grade3;
					}
				}
			}
		}
		
		if(RSW){
			
			for(iterator=MREGR;iterator<=NREGR;iterator++){
				
				if(cubed_ratio[iterator]!=0.0){
					
					BETA=parameters[TOTAL_INDICATORS*(sex-1)+iterator,1];
					MU=parameters[TOTAL_INDICATORS*(sex-1)+iterator,2];
					SIGMA=parameters[TOTAL_INDICATORS*(sex-1)+iterator,3];
					ZD=(cubed_ratio[iterator]-BETA*current_estimate-MU)/SIGMA;
					
					// Add up derivatives with respect to theta across continuous indicators
					deriv1=deriv1+BETA*ZD/SIGMA;
					deriv2=deriv2*sqr(BETA/SIGMA);
				}
			}
		}
		
		// If second derivative is negative, make it positive
		if(deriv2<0){
			
			deriv2=-deriv2;
		}
		
		// If second derivative is 0, make it 0.01
		if(deriv2==0){
			
			deriv2=0.01;
		}
		
		deriv_end=deriv1/deriv2;
		
		if(deriv_end>1){
			
			deriv_end=1;
		}
		
		if(deriv_end<-1){
			
			deriv_end=-1;
		}
		
		// Update the estimate
		current_estimate=current_estimate+deriv_end;
		
		// Store second derivative to compute standard error
		E=deriv2;
		
		// If the derivative has changed sign since the last iteration, then you have overshot the maximum
		if(iterator1>2 && (deriv1*deriv_holder<0)){
			
			if(deriv_holder>0 && current_estimate<T0){
				
				current_estimate=(T0+T1)/2.0;
			}
			
			if(deriv_holder<0 && current_estimate>T0){
				
				current_estimate=(T0+T1)/2.0;
			}
		}

	} while( (iterator1>50) || (abs(deriv1) < 0.001) );
	
	if(deriv1>0.0001){
		
		print("Algorithm did not converge. Beware of the results");
	}
	
	// Inverse of second derivative is the variance
	E=1.0/sqrt(E);
	
	// Was added later in the program. If it did not converge, then E is not the variance
	if(deriv1>0.0001){
		
		E=9999;
	}
	
	if(current_estimate<0){
		
		current_estimate=0;
		
		if(RUNMODE==0){
			
			print("The estimated skeletal age is less than zero.");
			print("This probably means that all indicators entered are ");
			print("in their immature state. No valid estimate exists.");
			print("The immature skeletal age code is %f.2", current_estimate);
			print("with an estimated standard error of %f.2 years.", E);
		}
	}
	
	if(((sex=1) && (current_estimate>18.00)) || ((sex=2) && (current_estimate>18.00))){
		
		if(sex==1){
			
			current_estimate=18.00;
			
		} else {
			
			current_estimate=18.00;
			
			if(RUNMODE==0){
				print("The estimated skeletal age is greater than 18.");
				print("This means that all or nearly all indicators entered are");
				print("in their mature state. No valid estimate exists.");
				print("The mature skeletal age code is %f.2 ", current_estimate);
				print("with an estimated standard error of %f.2 years.", E);
			}
		}
	}
	
	if((RUNMODE==0) && (current_estimate>0) && (((sex==1) && (current_estimate<18.00)) || ((sex==2) && (current_estimate<18.00)))){
		
		print("The estimated skeletal age is %f.2 years", current_estimate);
		print("with an estimated standard error of %f.2 years.", E);
		print("%d iterations were required.", iterator1);
	}
}