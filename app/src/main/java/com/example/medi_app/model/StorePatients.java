package com.example.medi_app.model;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class StorePatients extends Contract {
    public static final String BINARY = "60806040526000805534801561001457600080fd5b50610b58806100246000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c806357ea0c2f1161005b57806357ea0c2f146100d457806362c9953b146100e757806386a97bd41461010a57806393119a831461011d57600080fd5b8063239ee6c11461008257806335694bff146100975780634942e226146100aa575b600080fd5b610095610090366004610865565b610130565b005b6100956100a5366004610865565b6101a0565b6100bd6100b83660046108c9565b61024a565b6040516100cb929190610962565b60405180910390f35b6100bd6100e2366004610990565b61040a565b6100fa6100f53660046108c9565b610547565b60405190151581526020016100cb565b6100956101183660046109a9565b6105d8565b6100bd61012b366004610990565b6105fd565b6040805180820182528381526020808201849052600080548152600182529290922081518051929384936101679284920190610729565b5060208281015180516101809260018501920190610729565b50905050600160008082825461019691906109fc565b9091555050505050565b60005b60005481101561024557826040516020016101be9190610a14565b60408051601f1981840301815282825280516020918201206000858152600183529290922091926101f0929101610a6b565b604051602081830303815290604052805190602001201415610233576000818152600160208181526040909220845161023193919092019190850190610729565b505b8061023d81610b07565b9150506101a3565b505050565b60608060005b600054811015610404578360405160200161026b9190610a14565b60408051601f19818403018152828252805160209182012060008581526001835292909220919261029d929101610a6b565b6040516020818303038152906040528051906020012014156103f25760008181526001602081905260409091208054909182019082906102dc90610a30565b80601f016020809104026020016040519081016040528092919081815260200182805461030890610a30565b80156103555780601f1061032a57610100808354040283529160200191610355565b820191906000526020600020905b81548152906001019060200180831161033857829003601f168201915b5050505050915080805461036890610a30565b80601f016020809104026020016040519081016040528092919081815260200182805461039490610a30565b80156103e15780601f106103b6576101008083540402835291602001916103e1565b820191906000526020600020905b8154815290600101906020018083116103c457829003601f168201915b505050505090509250925050915091565b806103fc81610b07565b915050610250565b50915091565b6000818152600160208190526040909120805460609283929190820190829061043290610a30565b80601f016020809104026020016040519081016040528092919081815260200182805461045e90610a30565b80156104ab5780601f10610480576101008083540402835291602001916104ab565b820191906000526020600020905b81548152906001019060200180831161048e57829003601f168201915b505050505091508080546104be90610a30565b80601f01602080910402602001604051908101604052809291908181526020018280546104ea90610a30565b80156105375780601f1061050c57610100808354040283529160200191610537565b820191906000526020600020905b81548152906001019060200180831161051a57829003601f168201915b5050505050905091509150915091565b6000805b6000548110156105cf57826040516020016105669190610a14565b60408051601f198184030181528282528051602091820120600085815260018352929092209192610598929101610a6b565b6040516020818303038152906040528051906020012014156105bd5750600192915050565b806105c781610b07565b91505061054b565b50600092915050565b6000828152600160208181526040909220835161024593919092019190840190610729565b60016020526000908152604090208054819061061890610a30565b80601f016020809104026020016040519081016040528092919081815260200182805461064490610a30565b80156106915780601f1061066657610100808354040283529160200191610691565b820191906000526020600020905b81548152906001019060200180831161067457829003601f168201915b5050505050908060010180546106a690610a30565b80601f01602080910402602001604051908101604052809291908181526020018280546106d290610a30565b801561071f5780601f106106f45761010080835404028352916020019161071f565b820191906000526020600020905b81548152906001019060200180831161070257829003601f168201915b5050505050905082565b82805461073590610a30565b90600052602060002090601f016020900481019282610757576000855561079d565b82601f1061077057805160ff191683800117855561079d565b8280016001018555821561079d579182015b8281111561079d578251825591602001919060010190610782565b506107a99291506107ad565b5090565b5b808211156107a957600081556001016107ae565b634e487b7160e01b600052604160045260246000fd5b600082601f8301126107e957600080fd5b813567ffffffffffffffff80821115610804576108046107c2565b604051601f8301601f19908116603f0116810190828211818310171561082c5761082c6107c2565b8160405283815286602085880101111561084557600080fd5b836020870160208301376000602085830101528094505050505092915050565b6000806040838503121561087857600080fd5b823567ffffffffffffffff8082111561089057600080fd5b61089c868387016107d8565b935060208501359150808211156108b257600080fd5b506108bf858286016107d8565b9150509250929050565b6000602082840312156108db57600080fd5b813567ffffffffffffffff8111156108f257600080fd5b6108fe848285016107d8565b949350505050565b60005b83811015610921578181015183820152602001610909565b83811115610930576000848401525b50505050565b6000815180845261094e816020860160208601610906565b601f01601f19169290920160200192915050565b6040815260006109756040830185610936565b82810360208401526109878185610936565b95945050505050565b6000602082840312156109a257600080fd5b5035919050565b600080604083850312156109bc57600080fd5b82359150602083013567ffffffffffffffff8111156109da57600080fd5b6108bf858286016107d8565b634e487b7160e01b600052601160045260246000fd5b60008219821115610a0f57610a0f6109e6565b500190565b60008251610a26818460208701610906565b9190910192915050565b600181811c90821680610a4457607f821691505b60208210811415610a6557634e487b7160e01b600052602260045260246000fd5b50919050565b600080835481600182811c915080831680610a8757607f831692505b6020808410821415610aa757634e487b7160e01b86526022600452602486fd5b818015610abb5760018114610acc57610af9565b60ff19861689528489019650610af9565b60008a81526020902060005b86811015610af15781548b820152908501908301610ad8565b505084890196505b509498975050505050505050565b6000600019821415610b1b57610b1b6109e6565b506001019056fea2646970667358221220a8a7a04f74a10a9785d275f29bb467fab538d11f4fd6d372dbb032111bf7dcfa64736f6c634300080b0033";

    public static final String FUNC_ADDPATIENT = "addPatient";

    public static final String FUNC_CHECKPATIENTBYID = "checkPatientByID";

    public static final String FUNC_GETPATIENTBYID = "getPatientByID";

    public static final String FUNC_GETPATIENTBYINDEX = "getPatientByIndex";

    public static final String FUNC_PATIENTS = "patients";

    public static final String FUNC_UPDATEPATIENTDATABYID = "updatePatientDataByID";

    public static final String FUNC_UPDATEPATIENTDATABYINDEX = "updatePatientDataByIndex";

    @Deprecated
    protected StorePatients(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected StorePatients(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected StorePatients(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected StorePatients(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> addPatient(String _id, String _data) {
        final Function function = new Function(
                FUNC_ADDPATIENT, 
                Arrays.<Type>asList(new Utf8String(_id),
                new Utf8String(_data)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> checkPatientByID(String _id) {
        final Function function = new Function(FUNC_CHECKPATIENTBYID, 
                Arrays.<Type>asList(new Utf8String(_id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Tuple2<String, String>> getPatientByID(String _id) {
        final Function function = new Function(FUNC_GETPATIENTBYID, 
                Arrays.<Type>asList(new Utf8String(_id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple2<String, String>>(function,
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple2<String, String>> getPatientByIndex(BigInteger _index) {
        final Function function = new Function(FUNC_GETPATIENTBYINDEX, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple2<String, String>>(function,
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple2<String, String>> patients(BigInteger param0) {
        final Function function = new Function(FUNC_PATIENTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple2<String, String>>(function,
                new Callable<Tuple2<String, String>>() {
                    @Override
                    public Tuple2<String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> updatePatientDataByID(String _id, String _data) {
        final Function function = new Function(
                FUNC_UPDATEPATIENTDATABYID, 
                Arrays.<Type>asList(new Utf8String(_id),
                new Utf8String(_data)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updatePatientDataByIndex(BigInteger _index, String _data) {
        final Function function = new Function(
                FUNC_UPDATEPATIENTDATABYINDEX, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_index), 
                new Utf8String(_data)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static StorePatients load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new StorePatients(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static StorePatients load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new StorePatients(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static StorePatients load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new StorePatients(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static StorePatients load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new StorePatients(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<StorePatients> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(StorePatients.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<StorePatients> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(StorePatients.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<StorePatients> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(StorePatients.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<StorePatients> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(StorePatients.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
